package beziersurface;

public class Polinomio {

    private double resultX;
    private double resultY;
    private double resultZ;
    
    
    public void pegarPonto(Ponto[][] p, double s,double t) {
        
        double somaInterna1 = 0;
        double somaInterna2 = 0;
        double somaInterna3 = 0;
        
        int n = p.length-1;        
        int m = p.length-1;
        
                                               
        for(int i=0; i <= n; i++){            
            
            for(int j=0; j <= m; j++){
                
                double B1 = valorDeB(i,n);
                double B2 = valorDeB(j,m);
                
                somaInterna1 = somaInterna1 + ( Math.pow(s, i) * Math.pow(1-s, n-i) *  B1 )  * (Math.pow(t, j) * Math.pow(1-t, m-j) * B2) * p[i][j].getX();
              
                somaInterna2 = somaInterna2 + ( Math.pow(s, i) * Math.pow(1-s, n-i) *  B1 )  * (Math.pow(t, j) * Math.pow(1-t, m-j) * B2) * p[i][j].getY();
                
                somaInterna3 = somaInterna3 + ( Math.pow(s, i) * Math.pow(1-s, n-i) *  B1 )  * (Math.pow(t, j) * Math.pow(1-t, m-j) * B2) * p[i][j].getZ();
            }
        }
        resultX = somaInterna1;
        resultY = somaInterna2;
        resultZ = somaInterna3;
    }

    
    
    private double fatorial (double numero){
        
        double resultado = 1;
        
        for(double j = numero; j >=1; j--){
            
            resultado = resultado * j;
        }
        return resultado;
    }
    
    private double valorDeB(double inicio,double fim){
        
        return (fatorial(fim) / ( fatorial(inicio) * fatorial(fim-inicio) ) );
    }
    
    public double getResultZ() {
        return resultZ;
    }

    public double getResultX() {
        return resultX;
    }

    public double getResultY() {
        return resultY;
    }    
    
}
