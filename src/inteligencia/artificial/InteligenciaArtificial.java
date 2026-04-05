/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package inteligencia.artificial;

/**
 *
 * @author vinic
 */
import java.util.Random;
public class InteligenciaArtificial {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int n = 10;
        int[][] obstaculos = {
            {0,4}, {1,0}, {1,3}, {2,2}, {3,2}, {4,1}, {5,3}, 
            {3,5}, {5,5}, {6,5}, {7,5}, {8,5}, {9,5}, 
            {5,6}, {4,6}, {5,7}, {5,8}, {6,8}, {7,8}, {8,8}, {8,7}
        };
        
        // Simples
        System.out.println("ETAPA 1:");
        AmbienteSimples ambiente1 = new AmbienteSimples(n);
        ambiente1.executarSimulacao();
        System.out.println("\n\n");
        
        // Reativo baseado em modelo
        System.out.println("ETAPA 2:");
        AmbienteModelo ambiente2 = new AmbienteModelo(n, obstaculos);
        ambiente2.ExecutarSimulacao();
        ambiente2.exibirGrid();
        
        // Baseado em objetivos
        System.out.println("ETApa 3:");
        AmbienteObjetivo ambiente3 = new AmbienteObjetivo(n, obstaculos);
        ambiente3.ExecutarSimulacao();
        ambiente3.exibirGrid();
        
        // Etapa 4 - Variação 1 (Completamente Observável)
        System.out.println("\n\nETAPA 4 - Variacao 1:");
        AmbienteUtilidade amb1 = new AmbienteUtilidade(true);
        amb1.ExecutarSimulacao();
        amb1.exibirGrid();
        amb1.exibirModeloAgente();

        // Etapa 4 - Variação 2 (Parcialmente Observável)
        System.out.println("\n\nETAPA 4 - Variacao 2:");
        AmbienteUtilidade amb2 = new AmbienteUtilidade(false);
        amb2.ExecutarSimulacao();
        amb2.exibirGrid();
        amb2.exibirModeloAgente();
    }
    
}
