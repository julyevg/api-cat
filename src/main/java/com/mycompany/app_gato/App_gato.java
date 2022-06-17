/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.mycompany.app_gato;

import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author jass
 */
public class App_gato {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");
        int opcion_menu = -1;
        String[] botones = {"1. ver gatos","2. Favoritos", "3. Salir"};
        do {
            String opcion = (String) JOptionPane.showInputDialog(null, "Gattios java",
                    "Menu principal", JOptionPane.INFORMATION_MESSAGE,
                    null, botones, botones[0]);
            for (int i = 0; i < botones.length; i++) {
                if (opcion.equals(botones[i])) {
                    opcion_menu = i;
                }
            }
            switch(opcion_menu){
                case 0:
                    GatosService.verGatos();
                    break;
                case 1:
                    Gatos gato = new Gatos();
                    GatosService.verFavorito(gato.api_key);
                    break;
                default:
                    break;
                    }
        }
            while (opcion_menu != 1);
        }
    }

