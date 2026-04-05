/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inteligencia.artificial;

/**
 *
 * @author vinic
 */
public class CelulaUtilidade {
    private boolean bloqueada;
    private boolean visitada;
    private int custo;
    
    public CelulaUtilidade(int custo) {
        this.bloqueada = false;
        this.visitada = false;
        this.custo = custo;
    }

    public boolean isBloqueada() {
        return bloqueada;
    }

    public boolean isVisitada() {
        return visitada;
    }

    public int getCusto() {
        return custo;
    }

    public void setCusto(int custo) {
        this.custo = custo;
    }

    public void setBloqueada(boolean bloqueada) {
        this.bloqueada = bloqueada;
    }

    public void setVisitada(boolean visitada) {
        this.visitada = visitada;
    }

    
}
