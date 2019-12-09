package beziersurface;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Main {


    private static GLWindow window;
    public static int screenWidth = 700;
    public static int screenHeight = 500;
    public static Ponto[][] pontos;
    public static Scanner in = new Scanner(System.in);
    public static int tamanho;
    public static boolean leuArquivo = false;
    public static String nomeDoArquvio;
    public static double[][] transformacoes = new double [45][5];
    public static int qtdTransformacoes = 0;

    public static void LerArquivo(String nome) throws IOException {

        File arq = new File(nome + ".obj");
        FileReader fileR = null;
        BufferedReader bufferR = null;
        String readLine = "";
        String coordenadas[] = null;
        String grau[] = null;
        Ponto p;
        double x,y,z;
        int linha = 0, coluna =0;
        
        if (arq.exists()) {
            
            fileR = new FileReader(arq);
            bufferR = new BufferedReader(fileR);
            
            while (readLine != null) {
                
                readLine = bufferR.readLine();
                if (readLine == null) {
                    break;
                }
                if(readLine.charAt(0) == 'g'){ //grau
                                
                    grau = readLine.split("\t");
                    int valor = Integer.parseInt(grau[1]);
                    pontos = new Ponto[valor+1][valor+1];
                }    
                if (readLine.charAt(0) == 'v') { //pontos de controle

                    coordenadas = readLine.split("\t");
                    
                    x = Double.parseDouble(coordenadas[1]);
                    y = Double.parseDouble(coordenadas[2]);
                    z = Double.parseDouble(coordenadas[3]);
                  
                    if(linha != pontos.length){
                        pontos[linha][coluna] = new Ponto(x,y,z);
                    }
                    if(coluna < pontos.length ){
                                            
                        coluna ++;
                                            
                    }if(coluna == pontos.length){                        
                        linha = linha +1;
                        coluna = 0;
                    }                  
                
                }else if (readLine.charAt(0) == 't') { // tranlação

                    coordenadas = readLine.split("\t");
                    
                    transformacoes[qtdTransformacoes][0] =  1;
                    transformacoes[qtdTransformacoes][1] = Double.parseDouble(coordenadas[1]); //x
                    transformacoes[qtdTransformacoes][2] = Double.parseDouble(coordenadas[2]); //y
                    transformacoes[qtdTransformacoes][3] = Double.parseDouble(coordenadas[3]); //z
                    qtdTransformacoes++;
                
                }else if (readLine.charAt(0) == 's') { // escalonamento
  
                    coordenadas = readLine.split("\t");
                    transformacoes[qtdTransformacoes][0] =  2; 
                    transformacoes[qtdTransformacoes][1] = Double.parseDouble(coordenadas[1]); //x
                    transformacoes[qtdTransformacoes][2] = Double.parseDouble(coordenadas[2]); //y
                    transformacoes[qtdTransformacoes][3] = Double.parseDouble(coordenadas[3]); //z
                    qtdTransformacoes++;                
                
                } else if (readLine.charAt(0) == 'r') { //rotação

                    coordenadas = readLine.split("\t");
                    transformacoes[qtdTransformacoes][0] =  3;
                    
                    if(coordenadas[1].equalsIgnoreCase("x")){
                        transformacoes[qtdTransformacoes][1] = Double.parseDouble(coordenadas[2]); // rotação x
                        transformacoes[qtdTransformacoes][2] = 0 ; 
                        transformacoes[qtdTransformacoes][3] = 0 ; 
                    
                    }else if(coordenadas[1].equalsIgnoreCase("y")){
                        transformacoes[qtdTransformacoes][1] = 0; 
                        transformacoes[qtdTransformacoes][2] = Double.parseDouble(coordenadas[2]) ; //rotação eixo y
                        transformacoes[qtdTransformacoes][3] = 0 ; 
                    
                    }else{
                        transformacoes[qtdTransformacoes][1] = 0; 
                        transformacoes[qtdTransformacoes][2] = 0 ;  
                        transformacoes[qtdTransformacoes][3] = Double.parseDouble(coordenadas[2]) ; //rotação z
                    }
                    qtdTransformacoes++;
                }                
            }
            
            tamanho = pontos.length;
            bufferR.close();
            fileR.close();
        }
    }

    public static void init() {
        GLProfile.initSingleton();
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);
        window = GLWindow.create(caps);
        window.setSize(screenWidth, screenHeight);
        window.setTitle("Bezier Superficie");
        window.setResizable(true);
        window.addGLEventListener(new EventListener());
        window.addKeyListener(new KeyBoardInput());
        FPSAnimator animator = new FPSAnimator(window, 60); 
        animator.start();
        window.setVisible(true);
    }

    public static int getWindowWidth() {
        return window.getWidth();
    }

    public static int getWindowHeight() {
        return window.getHeight();
    }

    public static GLWindow getWindow() {
        return window;
    }

    public static void main(String[] args) throws IOException {
        
        System.out.print("File name: ");
        nomeDoArquvio = in.next();           
        LerArquivo(nomeDoArquvio);
        leuArquivo = true;
        init();
            
    }
}
