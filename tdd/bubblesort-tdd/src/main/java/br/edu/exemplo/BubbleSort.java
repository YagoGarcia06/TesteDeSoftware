package br.edu.exemplo;

public class BubbleSort {

    private int[] numeros;

    public BubbleSort(int[] numeros) {
        this.numeros = numeros;
    }

    /**
     * Ordena o vetor em ordem crescente usando Bubble Sort.
     * A cada passada o maior elemento restante "sobe" para o fim,
     * então a parte final já ordenada não precisa ser revisitada.
     * Se uma passada inteira não fizer nenhuma troca, o vetor já está ordenado.
     */
    public void ordenar() {
        for (int passada = 0; passada < numeros.length - 1; passada++) {
            boolean houveTroca = false;
            for (int j = 0; j < numeros.length - 1 - passada; j++) {
                if (numeros[j] > numeros[j + 1]) {
                    trocar(j, j + 1);
                    houveTroca = true;
                }
            }
            if (!houveTroca) {
                return;
            }
        }
    }

    private void trocar(int i, int j) {
        int aux = numeros[i];
        numeros[i] = numeros[j];
        numeros[j] = aux;
    }

    public int[] getNumeros() {
        return numeros;
    }
}
