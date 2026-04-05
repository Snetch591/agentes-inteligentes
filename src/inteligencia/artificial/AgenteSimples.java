/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inteligencia.artificial;

/**
 *
 * @author vinic
 */
public class AgenteSimples {
    private int x;
    private int y;
    private int n;
    private boolean norte = false;
    private boolean sul = false;
    private boolean oeste = false;
    private boolean leste = false;
    private String direcaoAtual = "LESTE";
    
    public AgenteSimples(int x, int y, int n) {
        this.x = x;
        this.y = y;
        this.n = n;
    }
    
    public boolean alcancouFronteiras() {
        return norte && sul && oeste && leste;
    }
    
    public void mover() {
        if (x == 0) norte = true;
        if (x == n -1) sul = true;
        if (y == 0 ) oeste = true;
        if (y == n - 1) leste = true;
        if (direcaoAtual.equals("LESTE") && y == n -1 ) {
            direcaoAtual = "SUL";
            System.out.println("Mudando para direcao sul");
        } else if (direcaoAtual.equals("SUL") && x == n - 1) {
            direcaoAtual = "OESTE";
            System.out.println("Mudando para direcao oeste");
        } else if (direcaoAtual.equals("OESTE") && y == 0) {
            direcaoAtual = "NORTE";
            System.out.println("Mudando para direcao norte");
        } else if (direcaoAtual.equals("NORTE") && x == 0) {
            direcaoAtual = "LESTE";
            System.out.println("Mudando para direcao leste");
        }
        
        int novoX = x;
        int novoY = y;
        
        switch (direcaoAtual) {
            case "LESTE": novoY++; break;
            case "NORTE": novoX--; break;
            case "OESTE": novoY--; break;
            case "SUL": novoX++; break;
        }
        
        if (novoX >= 0 && novoX < n && novoY >= 0 && novoY < n) {
            x = novoX;
            y = novoY;
            } else {
                System.out.println("Movimento bloqueado (fora do grid), ajustando direção...");
                return;
            }
        System.out.println("Movendo para " + direcaoAtual + " | Posicao atual: (" + x + ", " + y + ")");
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
}
