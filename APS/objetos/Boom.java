package objetos;

public class Boom {
    public Boom(){

    }

    public Boom(double tamanho, float angulo){
        this.tamanho = tamanho;
        this.angulo = angulo;
    }

    double tamanho;
    float angulo;

    public double getTamanho(){
        return tamanho;
    }

    public void setTamanho(double tamanho){
        this.tamanho = tamanho;
    }

    public double getAngulo(){
        return angulo;
    }

    public void setAngulo(float angulo){
        this.angulo = angulo;
    }
}
