import { onRequest } from "firebase-functions/v2/https";
import * as admin from "firebase-admin";
import cors from "cors";
import QRCode from "qrcode";
import crypto from "crypto";

admin.initializeApp();
const db = admin.firestore();

const corsHandler = cors({ origin: "*", optionsSuccessStatus: 200 });

export const performAuth = onRequest(async (req, res) => {
  corsHandler(req, res, async () => {
    const { siteURL, apiKey } = req.body;

    if (!siteURL || !apiKey) {
      return res.status(400).send("Requisição inválida.");
    }

    try {
      const querySnap = await db.collection("partners")
        .where("siteUrl", "==", siteURL)
        .limit(1)
        .get();

      if (querySnap.empty) {
        return res.status(403).send("Requisição inválida.");
      }

      const partnerData = querySnap.docs[0].data();

      if (!partnerData || partnerData.apiKey !== apiKey) {
        return res.status(403).send("Requisição inválida.");
      }

      const loginToken = crypto.randomBytes(192).toString("hex");

      await db.collection("login").doc(loginToken).set({
        apiKey,
        createdAt: admin.firestore.FieldValue.serverTimestamp(),
        loginToken,
      });

      const qrCodeDataURL = await QRCode.toDataURL(loginToken);
      return res.status(200).send({
        qrCode: qrCodeDataURL,
        loginToken
      });

    } catch (err) {
      console.error("Erro no performAuth:", err);
      return res.status(500).send("Erro interno ao gerar QR Code.");
    }
  });
});



export const getLoginStatus = onRequest((req, res) => {
    corsHandler(req, res, async () => {
      const { loginToken } = req.body;

      if (!loginToken) {
        return res.status(400).send({ status: "error", message: "Token não fornecido." });
      }

      try {
        const docRef = db.collection("login").doc(loginToken);
        const docSnap = await docRef.get();

        if (!docSnap.exists) {
          return res.status(404).send({ status: "expired" });
        }

        const loginData = docSnap.data();
        const now = admin.firestore.Timestamp.now();
        const createdAt = loginData?.createdAt;
        const attempts = loginData?.attempts ?? 0;

        if (
          !createdAt ||
          now.toMillis() - createdAt.toMillis() > 60000
        ) {
          await docRef.delete();
          return res.status(403).send({ status: "expired" });
        }
        if (attempts >= 3) {
          await docRef.delete();
          return res.status(403).send({ status: "expired" });
        }
        await docRef.update({ attempts: attempts + 1 });

        if (loginData.user) {
          return res.status(200).send({ status: "authenticated", userId: loginData.user });
        } else {
          return res.status(200).send({ status: "pending" });
        }
      } catch (error) {
        console.error("Erro em getLoginStatus:", error);
        return res.status(500).send({ status: "error", message: "Erro interno." });
      }
    });
  });