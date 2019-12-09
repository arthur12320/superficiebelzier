package beziersurface;

import com.jogamp.opengl.GL2;

public class Desenho {

    private Ponto[][] ctrlP;
    public static GL2 gl;
    private Polinomio poly;
    private double red;
    private double green;
    private double blue;
    private Ponto[][] pontos;

    public Desenho(Ponto[][] PontosDeControle) {
        this.ctrlP = PontosDeControle;
        poly = new Polinomio();
        red = 0;
        green = 1;
        blue = 0;
    }

    public GL2 getGl() {
        return gl;
    }

    public void setGl(GL2 gl) {
        this.gl = gl;
    }

    public void Draw(double qtdPontos) {
        
        double s, t;
        double px, py, pz;
        int total = (int)qtdPontos + 1; 
        pontos = new Ponto[total][total]; 
        // criar pontos
        for (int i = 0 ; i <= qtdPontos; i++) {

            s = i / qtdPontos;
            
            for (int j = 0; j <= qtdPontos; j++) {

                t = j / qtdPontos;
                poly.pegarPonto(ctrlP, t, s); // PONTO 1;
                px = poly.getResultX();
                py = poly.getResultY();
                pz = poly.getResultZ();                
                Ponto p = new Ponto(px,py,pz);
                if(i < pontos.length ){
                    pontos[i][j] = p;
                }
            }
        }
        gl.glColor3d(red, green, blue); //color
        //plot todos pontos
        
        for (int i = 0; i < pontos.length; i++) { //linhas
            
        
            for (int j = 0; j < pontos.length-1; j++) {
         
                gl.glBegin(GL2.GL_LINES);
                gl.glVertex3d(pontos[i][j].getX(), pontos[i][j].getY(), pontos[i][j].getZ());
                gl.glVertex3d(pontos[i][j+1].getX(), pontos[i][j+1].getY(), pontos[i][j+1].getZ());
                gl.glEnd();
                gl.glFlush();
            }
        }
        for (int j = 0; j < pontos.length; j++) { //colunas
         
            for (int i = 0; i < pontos.length-1; i++) {
         
                gl.glBegin(GL2.GL_LINES);
                gl.glVertex3d(pontos[i][j].getX(), pontos[i][j].getY(), pontos[i][j].getZ());
                gl.glVertex3d(pontos[i+1][j].getX(), pontos[i+1][j].getY(), pontos[i+1][j].getZ());
                gl.glEnd();
                gl.glFlush();
            }
        }
        
    }

    public void setRed(double red) {

        this.red = red;
    }

    public void setGreen(double green) {

        this.green = green / 255;
    }

    public void setBlue(double blue) {

        this.blue = blue / 255;
    }

    public Polinomio getPoly() {
        return poly;
    }

    public void setPoly(Polinomio poly) {
        this.poly = poly;
    }

}
