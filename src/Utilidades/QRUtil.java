package Utilidades;

import Modelos.Venta;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

public class QRUtil {

    public static BufferedImage generarQR(String texto, int ancho, int alto) throws Exception {
        BitMatrix matriz = new MultiFormatWriter().encode(texto, BarcodeFormat.QR_CODE, ancho, alto);
        return MatrixToImageWriter.toBufferedImage(matriz);
    }

    public static void guardarQRComoPNG(String texto, int ancho, int alto, String rutaArchivo) throws Exception {
        BitMatrix matriz = new MultiFormatWriter().encode(texto, BarcodeFormat.QR_CODE, ancho, alto);
        Path ruta = Paths.get(rutaArchivo);
        MatrixToImageWriter.writeToPath(matriz, "PNG", ruta);
    }

    /**
     * Genera una imagen de código QR a partir de un texto.
     *
     * @param texto El contenido que tendrá el QR.
     * @param width Ancho de la imagen.
     * @param height Alto de la imagen.
     * @return BufferedImage con el QR generado.
     */
    public static BufferedImage generarCodigoQR(String texto, int width, int height) {
        QRCodeWriter qrWriter = new QRCodeWriter();
        BufferedImage imagenQR = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        try {
            BitMatrix bitMatrix = qrWriter.encode(texto, BarcodeFormat.QR_CODE, width, height);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int color = bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF; // negro o blanco
                    imagenQR.setRGB(x, y, color);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return imagenQR;
    }

    public static BufferedImage generarImagenBoletoConQR(Venta venta, BufferedImage imagenQR) {
        int ancho = 400;
        int alto = 300;
        BufferedImage imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = imagen.createGraphics();

        // Fondo blanco
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, ancho, alto);

        // Texto
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        int x = 20;
        int y = 30;
        int salto = 20;

        g.drawString("BOLETO", x, y); y += salto;
        g.drawString("Película: " + venta.getPelicula(), x, y); y += salto;
        g.drawString("Sala: " + venta.getSala(), x, y); y += salto;
        g.drawString("Fecha y Hora: " + formato.format(venta.getFechaHoraFuncion()), x, y); y += salto;
        g.drawString("Entradas: " + venta.getCantidadEntradas(), x, y); y += salto;
        g.drawString("Total: $" + String.format("%.2f", venta.getTotal()), x, y); y += salto;
        g.drawString("Fecha Venta: " + formato.format(venta.getFechaVenta()), x, y); y += salto;

        // Dibuja QR al lado derecho
        if (imagenQR != null) {
            g.drawImage(imagenQR, ancho - 200, alto - 120, 100, 100, null);
        }

        g.dispose();
        return imagen;
    }


}

