package componentes;

public class Teclado {
    private boolean direita;
    private boolean esquerda;
    private boolean tecla_w;
    private boolean tecla_j;
    private boolean tecla_k;
    private boolean tecla_enter;

    public boolean isDireita(){
        return direita;
    }

    public boolean isEsquerda(){
        return esquerda;
    }

    public boolean isTecla_W(){
        return tecla_w;
    }

    public boolean isTecla_J(){
        return tecla_j;
    }

    public boolean isTecla_K(){
        return tecla_k;
    }

    public boolean isTecla_Enter(){
        return tecla_enter;
    }

    public void setDireita(boolean direita){
        this.direita = direita;
    }

    public void setEsquerda(boolean esquerda){
        this.esquerda = esquerda;
    }

    public void setTecla_W(boolean tecla_w){
        this.tecla_w = tecla_w;
    }

    public void setTecla_J(boolean tecla_j){
        this.tecla_j = tecla_j;
    }

    public void setTecla_K(boolean tecla_k){
        this.tecla_k = tecla_k;
    }

    public void setTecla_Enter(boolean tecla_enter){
        this.tecla_enter = tecla_enter;
    }
}
