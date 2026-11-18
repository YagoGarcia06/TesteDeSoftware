package br.edu.exemplo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class BubbleSortTest {

    @Test
    void deveOrdenarVetorDesordenado() {
        BubbleSort bubbleSort =
                new BubbleSort(new int[]{5, 3, 1, 4, 2});

        bubbleSort.ordenar();

        assertArrayEquals(
                new int[]{1, 2, 3, 4, 5},
                bubbleSort.getNumeros()
        );
    }

    @Test
    void deveManterVetorJaOrdenado() {
        BubbleSort bubbleSort =
                new BubbleSort(new int[]{1, 2, 3, 4, 5});

        bubbleSort.ordenar();

        assertArrayEquals(
                new int[]{1, 2, 3, 4, 5},
                bubbleSort.getNumeros()
        );
    }

    @Test
    void deveOrdenarVetorComValoresRepetidos() {
        BubbleSort bubbleSort =
                new BubbleSort(new int[]{3, 1, 3, 2, 1});

        bubbleSort.ordenar();

        assertArrayEquals(
                new int[]{1, 1, 2, 3, 3},
                bubbleSort.getNumeros()
        );
    }

    // ---- Casos de borda adicionados ----

    @Test
    @DisplayName("Deve ordenar vetor em ordem decrescente (pior caso, todas as passadas)")
    void deveOrdenarVetorEmOrdemDecrescente() {
        BubbleSort bubbleSort =
                new BubbleSort(new int[]{5, 4, 3, 2, 1});

        bubbleSort.ordenar();

        assertArrayEquals(
                new int[]{1, 2, 3, 4, 5},
                bubbleSort.getNumeros()
        );
    }

    @Test
    @DisplayName("Deve manter vetor vazio sem erro")
    void deveManterVetorVazio() {
        BubbleSort bubbleSort = new BubbleSort(new int[]{});

        bubbleSort.ordenar();

        assertArrayEquals(new int[]{}, bubbleSort.getNumeros());
    }

    @Test
    @DisplayName("Deve manter vetor com um único elemento")
    void deveManterVetorComUmElemento() {
        BubbleSort bubbleSort = new BubbleSort(new int[]{7});

        bubbleSort.ordenar();

        assertArrayEquals(new int[]{7}, bubbleSort.getNumeros());
    }

    @Test
    @DisplayName("Deve ordenar vetor com números negativos e zero")
    void deveOrdenarVetorComNegativos() {
        BubbleSort bubbleSort =
                new BubbleSort(new int[]{3, -1, 0, -5, 2});

        bubbleSort.ordenar();

        assertArrayEquals(
                new int[]{-5, -1, 0, 2, 3},
                bubbleSort.getNumeros()
        );
    }

    @Test
    @DisplayName("Deve manter vetor com todos os valores iguais")
    void deveManterVetorComValoresIguais() {
        BubbleSort bubbleSort = new BubbleSort(new int[]{4, 4, 4});

        bubbleSort.ordenar();

        assertArrayEquals(new int[]{4, 4, 4}, bubbleSort.getNumeros());
    }
}
