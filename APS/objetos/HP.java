package objetos;

public class HP {
    
    public HP(double MAX_HP, double HP_atual){
        this.MAX_HP = MAX_HP;
        this.HP_atual = HP_atual;
    }

    public HP(){}
    
    private double MAX_HP;
    private double HP_atual;

    public double getMAX_HP(){
        return MAX_HP;
    }

    public void setMax_HP(double MAX_HP){
        this.MAX_HP = MAX_HP;
    }

    public double getHP_Atual(){
        return HP_atual;
    }

    public void setHP_Atual(double HP_atual){
        this.HP_atual = HP_atual;
    }

}
