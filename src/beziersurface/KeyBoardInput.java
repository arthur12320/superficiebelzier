package beziersurface;

import static beziersurface.Main.qtdTransformacoes;
import static beziersurface.Main.transformacoes;
import static beziersurface.EventListener.pontosOriginais;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.opengl.GLWindow;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class KeyBoardInput implements KeyListener {

    public static boolean mexerPonto = false;         
    private GLWindow window = Main.getWindow();
    public static boolean aparecerPolinomios = false;
    public static int sumirFrase = 0;               
    public static int percorrerTrans = 0;             
    public static boolean transformei = false;
    public static String poly = "Depois da transformação";
    public boolean fullscreen = false;


    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_P) { // pontos de controle

            if (sumirFrase == 0) {
                aparecerPolinomios = true;
                sumirFrase = 1;
            } else {
                aparecerPolinomios = false;
                sumirFrase = 0;
            }

        }
        if (e.getKeyCode() == KeyEvent.VK_Q || e.getKeyCode() == KeyEvent.VK_ESCAPE) { // close   
            window.getAnimator().stop();
            window.destroy();
        }
        if (e.getKeyCode() == KeyEvent.VK_F) { //f == fullscreen
            if(!fullscreen){
               window.setFullscreen(true);
               fullscreen = true;
            }else{
                window.setFullscreen(false);
                fullscreen = false;
            } 
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) { // barra de espaço  == começar transformações

            if (percorrerTrans < qtdTransformacoes) {

                transformei = true;

                if (transformacoes[percorrerTrans][0] == 1) { // TRANSLAÇÃO TEXTO

                    poly = "Translação x: " + transformacoes[percorrerTrans][1] + " , y: " + transformacoes[percorrerTrans][2] + " e z: " + transformacoes[percorrerTrans][3];

                } else if (transformacoes[percorrerTrans][0] == 2) { // ESCALONAMENTO TEXTO

                    poly = "Escalonamento x: " + transformacoes[percorrerTrans][1] + " , y: " + transformacoes[percorrerTrans][2] + " e z: " + transformacoes[percorrerTrans][3];

                } else { // ROTAÇÃO TEXTO

                    if (transformacoes[percorrerTrans][1] != 0) {

                        poly = "Rotação de " + transformacoes[percorrerTrans][1] + " graus no eixo x ";

                    } else if (transformacoes[percorrerTrans][2] != 0) {

                        poly = "Rotação de " + transformacoes[percorrerTrans][2] + " graus no eixo y ";

                    } else {

                        poly = "Rotação de " + transformacoes[percorrerTrans][3] + " graus no eixo z ";
                    }
                }
                percorrerTrans++;

            } else { // fim das transformações
                transformei = false;
                percorrerTrans = 0;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
