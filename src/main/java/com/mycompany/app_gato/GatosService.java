/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app_gato;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author jass
 */
public class GatosService {

    public static void verGatos() throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/images/search")
                .method("GET", null)
                .addHeader("Authorization", "Bearer 7ef350d0-11b2-4c30-a801-7ba2147a92d0")
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();

        String elJson = response.body().string();
        elJson = elJson.substring(1, elJson.length());
        elJson = elJson.substring(0, elJson.length() - 1);

        Gson gson = new Gson();
        Gatos gatos = gson.fromJson(elJson, Gatos.class);
        Image image = null;
        try {
            URL url = new URL(gatos.getUrl());
            image = ImageIO.read(url);
            ImageIcon fondoGato = new ImageIcon(image);

            if (fondoGato.getIconWidth() > 800) {
                Image fondo = fondoGato.getImage();
                Image modificada = fondo.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
                fondoGato = new ImageIcon(modificada);

            }
            String menu = " Opciones: \n"
                    + "1. Ver otra imagen \n"
                    + "2. Favorito \n"
                    + "3. Volver \n ";
            String[] botones = {"ver otra imagen", "favotiro", "volver"};
            String id_gato = String.valueOf(gatos.getId());
            String opcion = (String) JOptionPane.showInputDialog(null, menu, id_gato,
                    JOptionPane.INFORMATION_MESSAGE, fondoGato, botones, botones[0]);
            int seleccion = -1;

            for (int i = 0; i < botones.length; i++) {
                if (opcion.equals(botones[i])) {
                    seleccion = i;
                }
            }
            switch (seleccion) {
                case 0:
                    verGatos();
                    break;
                case 1:
                    favoritoGato(gatos);

                    break;
                default:
                    break;

            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void favoritoGato(Gatos gato) throws IOException {

        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType,
                    "{\n    \"image_id\":\"" + gato.getId() + "\"\n}");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", gato.getApi_key())
                    .build();
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void verFavorito(String api_key) throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/favourites")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("x-api-key", api_key)
                .build();
        Response response = client.newCall(request).execute();
        String elJSon = response.body().string();
        Gson gson = new Gson();

        GatosFav[] gatosArray = gson.fromJson(elJSon, GatosFav[].class);
        if (gatosArray.length > 0) {
            int min = 1;
            int max = gatosArray.length;
            int aleatorio = (int) (Math.random() * ((max - min) + 1)) + min;
            int indice = aleatorio - 1;
            GatosFav gatofav = gatosArray[indice];

            Image image = null;
            try {
                URL url = new URL(gatofav.image.getUrl());
                image = ImageIO.read(url);
                ImageIcon fondoGato = new ImageIcon(image);

                if (fondoGato.getIconWidth() > 800) {
                    Image fondo = fondoGato.getImage();
                    Image modificada = fondo.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
                    fondoGato = new ImageIcon(modificada);

                }
                String menu = " Opciones: \n"
                        + "1. Ver otra imagen \n"
                        + "2. Eliminar favorito \n"
                        + "3. Volver \n ";
                String[] botones = {"ver otra imagen", "eliminar favotiro", "volver"};
                String id_gato = String.valueOf(gatofav.getId());
                String opcion = (String) JOptionPane.showInputDialog(null, menu, id_gato,
                        JOptionPane.INFORMATION_MESSAGE, fondoGato, botones, botones[0]);
                int seleccion = -1;

                for (int i = 0; i < botones.length; i++) {
                    if (opcion.equals(botones[i])) {
                        seleccion = i;
                    }
                }
                switch (seleccion) {
                    case 0:
                        verFavorito(api_key);
                        break;
                    case 1:
                        borrarFavorito(gatofav);

                        break;
                    default:
                        break;

                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public static void borrarFavorito(GatosFav gatofav) {

        try {

            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites/"+gatofav.getId())
                    .method("DELETE", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", gatofav.api_key)
                        
                    .build();
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
