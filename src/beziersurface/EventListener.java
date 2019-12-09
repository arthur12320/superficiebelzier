package beziersurface;

import static beziersurface.KeyBoardInput.percorrerTrans;
import static beziersurface.KeyBoardInput.poly;
import static beziersurface.KeyBoardInput.transformei;
import static beziersurface.Main.transformacoes;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

public class EventListener implements GLEventListener {

    public static Desenho surperficieDepois;             // transformações nessa curva
    public static Desenho surperficieAntes;             //curva antes de transformações
    public static int qtdPonto;                   
    public static int queDesenho = 0;                    
    boolean tenhoDesenho = false;
    int percorrerPontos = 0;
    boolean criei2Telas = false;
    int i = 0;
    public static GLU glu = new GLU();
    private double rotatex = 1;
    public static GL2 gl;
    GLUT glut = new GLUT();
    public static Ponto[][] pontosOriginais = Main.pontos;

    @Override
    public void display(GLAutoDrawable drawable) {

        if (Main.leuArquivo) { 

            if (tenhoDesenho == false) {
                surperficieDepois = new Desenho(pontosOriginais);
                surperficieDepois.setGl(gl);
                tenhoDesenho = true;
                surperficieAntes = new Desenho(pontosOriginais);
                surperficieAntes.setGl(gl);
                surperficieAntes.setRed(0);
                surperficieAntes.setGreen(255);
                surperficieAntes.setBlue(255);
            }

        }
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslated(-0.5, 0, -1.5); //set zoom

        if (KeyBoardInput.transformei && tenhoDesenho == true) { // TENHO TRANSFORMAÇÕES LOGO, TENHO QUE MOSTRAR 2 SUPERFICIES UMA ANTES E A OUTRA DEPOIS

            if (percorrerTrans - 1 >= 1) { // SEGUNDA TRANSFORMAÇÃO
                
                // ------------------------------------------------------------------------------------------------- TELA DA ESQUERDA
                gl.glPushMatrix();
                gl.glViewport(0, 0, Main.getWindowWidth() / 2, Main.getWindowHeight()); 
                gl.glColor3d(1, 1, 1);         // cor do texto
                gl.glRasterPos3d(-0.2, 0.7,0); 
                glut.glutBitmapString(5, "Antes " + percorrerTrans); 

                gl.glLineWidth(3);     
                gl.glColor3d(0, 1, 0); 
                gl.glBegin(GL2.GL_LINES);  
                gl.glVertex3d(-0.35, -0.8,0);
                gl.glVertex3d(Main.getWindowWidth(),-0.8,0);
                gl.glEnd();

                gl.glColor3d(0, 0, 1);  
                gl.glBegin(GL2.GL_LINES); 
                gl.glVertex3d(-0.35, -0.8,0);
                gl.glVertex3d(-0.35, Main.getWindowHeight(),0);
                gl.glEnd();
                
                for (int i = 0; i < percorrerTrans - 1; i++) { // pegar transformações

                    if (transformacoes[i][0] == 1) { // TRANSLAÇÃO

                        gl.glTranslated(transformacoes[i][1], transformacoes[i][2], transformacoes[i][3]);

                    } else if (transformacoes[i][0] == 2) { // ESCALONAMENTO

                        gl.glScaled(transformacoes[i][1], transformacoes[i][2], transformacoes[i][3]);

                    } else { // ROTAÇÃO

                        if (transformacoes[i][1] != 0) { // NO EIXO X

                            gl.glRotated(transformacoes[i][1], 1, 0, 0);

                        } else if (transformacoes[i][2] != 0) { // NO EIXO Y

                            gl.glRotated(transformacoes[i][2], 0, 1, 0);

                        } else {                                               // NO EIXO Z
                            gl.glRotated(transformacoes[i][3], 0, 0, 1);
                        }
                    }
                }
                surperficieAntes.Draw(40.0); // DESENHA A CURVA
                gl.glPopMatrix();

                // ------------------------------------------------------------------------------------------------------------- TELA DA DIREITA 
                
                gl.glViewport(Main.getWindowWidth() / 2, 0, Main.getWindowWidth() / 2, Main.getWindowHeight()); // POSIÇÃO DA TELA DIREITA                
                gl.glColor3d(1, 1, 1); // COR DO TEXTO
                gl.glRasterPos3d(-0.1, 0.7,0); // POSIÇÃO X E Y QUE O TEXTO TERÁ
                glut.glutBitmapString(5, poly);

                gl.glLineWidth(3); // GROSSURA DA LINHA X (VERDE)
                gl.glColor3d(0, 1, 0); // COR DO EIXO X
                gl.glBegin(GL2.GL_LINES); // DESENHA EIXO X
                gl.glVertex3d(-0.35, -0.8,0);
                gl.glVertex3d(Main.getWindowWidth(), -0.8,0);
                gl.glEnd();
                gl.glColor3d(0, 0, 1); // COR EIXO Y
                gl.glBegin(GL2.GL_LINES); // DESENHA LINHA DO EIXO Y 
                gl.glVertex3d(-0.35, -0.8,0);
                gl.glVertex3d(-0.35, Main.getWindowHeight(),0);
                gl.glEnd();

               
                for (int i = 0; i < percorrerTrans ; i++) { // PEGAR TODAS AS TRANSFORMAÇÕES ANTERIORES

                    if (transformacoes[i][0] == 1) { // TRANSLAÇÃO

                        gl.glTranslated(transformacoes[i][1], transformacoes[i][2], transformacoes[i][3]);

                    } else if (transformacoes[i][0] == 2) { // ESCALONAMENTO

                        gl.glScaled(transformacoes[i][1], transformacoes[i][2], transformacoes[i][3]);

                    } else { // ROTAÇÃO

                        if (transformacoes[i][1] != 0) { // NO EIXO X

                            gl.glRotated(transformacoes[i][1], 1, 0, 0);

                        } else if (transformacoes[i][2] != 0) { // NO IXO Y

                            gl.glRotated(transformacoes[i][2], 0, 1, 0);

                        } else {                                               // NO EIXO Z
                            gl.glRotated(transformacoes[i][3], 0, 0, 1);
                        }
                    }
                }

                surperficieDepois.Draw(40.0);
                gl.glLoadIdentity();
                gl.glPopMatrix();

            } else { // PRIMEIRA TRANSFORMAÇÃO
     
                // ------------------------------------------------------------------------------------------------------------------------- TELA DA ESQUERDA
                
                gl.glPushMatrix();
                gl.glViewport(0, 0, Main.getWindowWidth() / 2, Main.getWindowHeight()); // POSIÇÃO DA TELA DA ESQUERDA
                gl.glColor3d(1, 1, 1);         // COR DO TEXTO
                gl.glRasterPos3d(-0.2, 0.7,0); // POSIÇÃO X E Y QUE O TEXTO TERÁ
                glut.glutBitmapString(5, "Antes " + percorrerTrans); //PLOTAR O DESENHO ONDE 5 É A FONTE E "Antes da transformação é o texto" 

                gl.glLineWidth(3);     // GROSSURA DA LINHA X (VERDE)
                gl.glColor3d(0, 1, 0); // COR DO EIXO X
                gl.glBegin(GL2.GL_LINES); // DESENHA EIXO X NA TEKA
                gl.glVertex3d(-0.35, -0.8,0);
                gl.glVertex3d(Main.getWindowWidth(),-0.8,0);
                gl.glEnd();

                gl.glColor3d(0, 0, 1); // COR DA LINHA Y (AZUL)
                gl.glBegin(GL2.GL_LINES); // DESENHA LINHA DO EIXO Y NA TELA
                gl.glVertex3d(-0.35, -0.8,0);
                gl.glVertex3d(-0.35, Main.getWindowHeight(),0);
                gl.glEnd();
                
                surperficieAntes.Draw(40.0); // DESENHA A FIGURA

                //--------------------------------------------------------------------------------------------------------------- TELA DA DIREITA
                
                gl.glViewport(Main.getWindowWidth() / 2, 0, Main.getWindowWidth() / 2, Main.getWindowHeight()); // POSIÇÃO DA TELA DIREITA                
                gl.glColor3d(1, 1, 1); // COR DO TEXTO
                gl.glRasterPos3d(-0.1, 0.7,0); // POSIÇÃO X E Y QUE O TEXTO TERÁ
                glut.glutBitmapString(5, poly);

                gl.glLineWidth(3); // GROSSURA DA LINHA X (VERDE)
                gl.glColor3d(0, 1, 0); // COR DO EIXO X
                gl.glBegin(GL2.GL_LINES); // DESENHA EIXO X
                gl.glVertex3d(-0.35, -0.8,0);
                gl.glVertex3d(Main.getWindowWidth(), -0.8,0);
                gl.glEnd();
                gl.glColor3d(0, 0, 1); // COR EIXO Y
                gl.glBegin(GL2.GL_LINES); // DESENHA LINHA DO EIXO Y 
                gl.glVertex3d(-0.35, -0.8,0);
                gl.glVertex3d(-0.35, Main.getWindowHeight(),0);
                gl.glEnd();
                                                                                       
               // --------------------------------------------------------------------------------------------------- REALIZA UMA TRANSFORMAÇÃO 
               
                if (transformacoes[percorrerTrans - 1][0] == 1) { // TRANSLAÇÃO

                    gl.glTranslated(transformacoes[percorrerTrans - 1][1], transformacoes[percorrerTrans - 1][2], transformacoes[percorrerTrans - 1][3]);

                } else if (transformacoes[percorrerTrans - 1][0] == 2) { // ESCALONAMENTO

                    gl.glScaled(transformacoes[percorrerTrans - 1][1], transformacoes[percorrerTrans - 1][2], transformacoes[percorrerTrans - 1][3]);

                } else { // ROTAÇÃO

                    if (transformacoes[percorrerTrans - 1][1] != 0) { // NO EIXO X

                        gl.glRotated(transformacoes[percorrerTrans - 1][1], 1, 0, 0);

                    } else if (transformacoes[percorrerTrans - 1][2] != 0) { // NO IXO Y

                        gl.glRotated(transformacoes[percorrerTrans - 1][2], 0, 1, 0);

                    } else {                                               // NO EIXO Z
                        gl.glRotated(transformacoes[percorrerTrans - 1][3], 0, 0, 1);
                    }

                }
                surperficieDepois.Draw(40.0);
                gl.glLoadIdentity();
                gl.glPopMatrix();

            }

        } else if (KeyBoardInput.transformei == false && tenhoDesenho == true) { // VOLTAR PARA UMA CURVA 

            if (criei2Telas && KeyBoardInput.transformei == false) {

                gl.glLoadIdentity();
                gl.glPopMatrix();
                criei2Telas = false;
            } else {
                criei2Telas = true;
                gl.glPushMatrix();
            }
            surperficieDepois.Draw(40.0);
        }

        if (KeyBoardInput.aparecerPolinomios && transformei == false) {  // APARECER O POLINOMIO 

            // CRIA UMA LINHA ENTRE O PONTO DE CONTROLE
            for (int i = 0; i < pontosOriginais.length; i++) {

                for (int j = 0; j < pontosOriginais[0].length - 1; j++) {

                    gl.glColor3d(1, 1, 1);// COR DOS PONTOS DE CONTROLE
                    gl.glBegin(GL2.GL_LINES);
                    gl.glVertex3d(pontosOriginais[i][j].getX(), pontosOriginais[i][j].getY(), pontosOriginais[i][j].getZ());
                    gl.glVertex3d(pontosOriginais[i][j + 1].getX(), pontosOriginais[i][j + 1].getY(), pontosOriginais[i][j + 1].getZ());
                    gl.glEnd();
                }
            }
            for (int j = 0; j < pontosOriginais[0].length; j++) {

                for (int i = 0; i < pontosOriginais.length - 1; i++) {
                    gl.glColor3d(1, 1, 1);// COR DOS PONTOS DE CONTROLE
                    gl.glBegin(GL2.GL_LINES);
                    gl.glVertex3d(pontosOriginais[i][j].getX(), pontosOriginais[i][j].getY(), pontosOriginais[i][j].getZ());
                    gl.glVertex3d(pontosOriginais[i + 1][j].getX(), pontosOriginais[i + 1][j].getY(), pontosOriginais[i + 1][j].getZ());
                    gl.glEnd();
                }
            }
        }
        //System.out.println(rotatex);
        //rotatex -= 1;

    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // TODO Auto-generated method stub
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        if (Main.leuArquivo) {
            qtdPonto = pontosOriginais.length;
        }
        gl = drawable.getGL().getGL2();
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glClearColor(0, 0, 0, 0); //Background Color
        gl.glClearDepth(1.0);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

        // TODO Auto-generated method stub
        gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        //gl.glOrtho(-250, 250, -350, 350,0.2,50);
        glu.gluPerspective(60, 1.0, 1.0, 30.0); //glu.gluPerspective(fovy, aspect, zNear, zFar);
        // Quanto mais fovy mais longe fica o objeto
//       glu.gluLookAt(3.00, 3.50, -5.00, // EYE
//                     0.00, 0.00, 0.00, // CENTER
//                     0.00, 0.00, 0.00); // UP

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

}
