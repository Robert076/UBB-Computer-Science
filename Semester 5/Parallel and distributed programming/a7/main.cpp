#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <time.h>

/**
 * POLYNOMIAL MULTIPLICATION WITH MPI - FINAL VERSION
 * ===================================================
 *
 * Implementare CORECTĂ a multiplicării polinoamelor folosind MPI
 * pe arhitectură distribuită (multiple noduri/procese).
 *
 *  BUGFIX: Toate procesele apelează MPI_Finalize()
 *  BUGFIX: Protecție bounds checking pe toți array accesses
 *  BUGFIX: Error handling pentru MPI calls
 *  BUGFIX: Memory cleanup corect
 *
 * Algoritmi implementați:
 * 1. Klasic O(n²) cu distribuție pe procese
 * 2. Karatsuba cu distribuție MPI
 *
 * Referințe MPI din cursul "Parallel Programming with MPI - Dr. Georg Hager":
 * - MPI_Init / MPI_Finalize: pg. 12
 * - MPI_Comm_rank / MPI_Comm_size: pg. 14
 * - MPI_Send / MPI_Recv: pg. 20 (Blocking point-to-point communication)
 * - MPI_Bcast: Collective communication
 * - MPI_Reduce: Reduction operations
 */

#define MPI_SAFE_CALL(call) \
    do { \
        int err = (call); \
        if (err != MPI_SUCCESS) { \
            char error_string[MPI_MAX_ERROR_STRING]; \
            int len; \
            MPI_Error_string(err, error_string, &len); \
            fprintf(stderr, "[Rank ?] MPI Error: %s\n", error_string); \
            MPI_Abort(MPI_COMM_WORLD, err); \
        } \
    } while(0)

typedef struct {
    double *coefficients;
    int degree;
} Polynomial;

Polynomial* create_polynomial(int degree) {
    if (degree < 0) degree = 0;

    Polynomial *p = (Polynomial*)malloc(sizeof(Polynomial));
    if (p == NULL) {
        fprintf(stderr, "ERROR: malloc failed for Polynomial struct\n");
        return NULL;
    }

    p->coefficients = (double*)calloc(degree + 1, sizeof(double));
    if (p->coefficients == NULL) {
        fprintf(stderr, "ERROR: calloc failed for coefficients (size=%d)\n", degree + 1);
        free(p);
        return NULL;
    }

    p->degree = degree;
    return p;
}

void free_polynomial(Polynomial *p) {
    if (p != NULL) {
        if (p->coefficients != NULL)
            free(p->coefficients);
        free(p);
    }
}

Polynomial* generate_random_polynomial(int degree, unsigned int seed) {
    Polynomial *p = create_polynomial(degree);
    if (p == NULL) return NULL;

    srand(seed);
    for (int i = 0; i <= degree; i++) {
        p->coefficients[i] = (double)(rand() % 100) + (double)rand() / RAND_MAX;
    }
    return p;
}

int polynomials_equal(Polynomial *p1, Polynomial *p2, double tolerance) {
    if (p1 == NULL || p2 == NULL) return 0;

    int maxDeg = (p1->degree > p2->degree) ? p1->degree : p2->degree;

    for (int i = 0; i <= maxDeg; i++) {
        double c1 = (i <= p1->degree && i >= 0) ? p1->coefficients[i] : 0.0;
        double c2 = (i <= p2->degree && i >= 0) ? p2->coefficients[i] : 0.0;

        if (fabs(c1 - c2) > tolerance) {
            return 0;
        }
    }
    return 1;
}

int next_power_of_two(int n) {
    if (n <= 1) return 1;
    n--;
    n |= n >> 1;
    n |= n >> 2;
    n |= n >> 4;
    n |= n >> 8;
    n |= n >> 16;
    n++;
    return n;
}

// ============================================================================
// O(n²) - sequent
// ial
// ============================================================================

Polynomial* multiply_classical_sequential(Polynomial *P, Polynomial *Q) {
    if (P == NULL || Q == NULL) return NULL;

    int n = P->degree + 1;
    int m = Q->degree + 1;
    int result_degree = P->degree + Q->degree;

    Polynomial *result = create_polynomial(result_degree);
    if (result == NULL) return NULL;

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            if (i + j <= result->degree) {
                result->coefficients[i + j] += P->coefficients[i] * Q->coefficients[j];
            }
        }
    }

    return result;
}

// ============================================================================
// O(n²) - MPI
// ============================================================================

/**
 * MPI_Bcast
 * MPI_Reduce
 */
Polynomial* multiply_classical_mpi(Polynomial *P, Polynomial *Q,
                                   int rank, int comm_size) {
    if (rank == 0 && (P == NULL || Q == NULL)) return NULL;

    int n = 0, m = 0;
    int result_degree = 0;
    int result_size = 0;

    if (rank == 0) {
        n = P->degree + 1;
        m = Q->degree + 1;
        result_degree = P->degree + Q->degree;
        result_size = result_degree + 1;
    }

    MPI_SAFE_CALL(MPI_Bcast(&n, 1, MPI_INT, 0, MPI_COMM_WORLD));
    MPI_SAFE_CALL(MPI_Bcast(&m, 1, MPI_INT, 0, MPI_COMM_WORLD));
    MPI_SAFE_CALL(MPI_Bcast(&result_size, 1, MPI_INT, 0, MPI_COMM_WORLD));

    double *P_coeffs = (double*)malloc(n * sizeof(double));
    double *Q_coeffs = (double*)malloc(m * sizeof(double));

    if (P_coeffs == NULL || Q_coeffs == NULL) {
        fprintf(stderr, "[Rank %d] ERROR: malloc failed for P_coeffs or Q_coeffs\n", rank);
        MPI_Abort(MPI_COMM_WORLD, 1);
    }

    if (rank == 0) {
        memcpy(P_coeffs, P->coefficients, n * sizeof(double));
        memcpy(Q_coeffs, Q->coefficients, m * sizeof(double));
    }

    MPI_SAFE_CALL(MPI_Bcast(P_coeffs, n, MPI_DOUBLE, 0, MPI_COMM_WORLD));
    MPI_SAFE_CALL(MPI_Bcast(Q_coeffs, m, MPI_DOUBLE, 0, MPI_COMM_WORLD));

    double *local_result = (double*)calloc(result_size, sizeof(double));
    if (local_result == NULL) {
        fprintf(stderr, "[Rank %d] ERROR: calloc failed for local_result\n", rank);
        MPI_Abort(MPI_COMM_WORLD, 1);
    }

    for (int i = rank; i < n; i += comm_size) {
        for (int j = 0; j < m; j++) {
            if (i + j < result_size) {
                local_result[i + j] += P_coeffs[i] * Q_coeffs[j];
            }
        }
    }

    double *global_result = (double*)calloc(result_size, sizeof(double));
    if (global_result == NULL) {
        fprintf(stderr, "[Rank %d] ERROR: calloc failed for global_result\n", rank);
        MPI_Abort(MPI_COMM_WORLD, 1);
    }

    MPI_SAFE_CALL(MPI_Reduce(local_result, global_result, result_size, MPI_DOUBLE,
                             MPI_SUM, 0, MPI_COMM_WORLD));

    Polynomial *result = NULL;
    if (rank == 0) {
        result = create_polynomial(result_degree);
        if (result != NULL) {
            memcpy(result->coefficients, global_result, result_size * sizeof(double));
        }
    }

    free(P_coeffs);
    free(Q_coeffs);
    free(local_result);
    free(global_result);

    return result;
}

// ============================================================================
// ALGORITM KARATSUBA - SECVENTIAL
// ============================================================================

/**
 * Adună doi polinomi
 */
Polynomial* add_polynomials(Polynomial *P, Polynomial *Q) {
    if (P == NULL || Q == NULL) return NULL;

    int maxDeg = (P->degree > Q->degree) ? P->degree : Q->degree;
    Polynomial *result = create_polynomial(maxDeg);
    if (result == NULL) return NULL;

    for (int i = 0; i <= P->degree && i <= maxDeg; i++)
        result->coefficients[i] += P->coefficients[i];
    for (int i = 0; i <= Q->degree && i <= maxDeg; i++)
        result->coefficients[i] += Q->coefficients[i];

    return result;
}

/**
 * Extrage partea inferioară a unui polinom (coeficienți 0..k-1)
 */
Polynomial* get_low_part(Polynomial *P, int k) {
    if (P == NULL || k <= 0) return NULL;
    if (k > P->degree + 1) k = P->degree + 1;

    Polynomial *low = create_polynomial(k - 1);
    if (low == NULL) return NULL;

    memcpy(low->coefficients, P->coefficients, k * sizeof(double));
    return low;
}

/**
 * Extrage partea superioară a unui polinom (coeficienți k..n)
 */
Polynomial* get_high_part(Polynomial *P, int k) {
    if (P == NULL || k <= 0) return NULL;

    int high_size = P->degree + 1 - k;
    if (high_size <= 0) return create_polynomial(0);

    Polynomial *high = create_polynomial(high_size - 1);
    if (high == NULL) return NULL;

    memcpy(high->coefficients, P->coefficients + k, high_size * sizeof(double));
    return high;
}

/**
 * Padding: Completeaza un polinom cu zerouri până la gradul n
 */
Polynomial* pad_polynomial(Polynomial *P, int n) {
    if (P == NULL || n <= 0) return NULL;

    Polynomial *padded = create_polynomial(n - 1);
    if (padded == NULL) return NULL;

    int copy_size = (P->degree + 1 < n) ? (P->degree + 1) : n;
    memcpy(padded->coefficients, P->coefficients, copy_size * sizeof(double));
    return padded;
}

/**
 * Combină rezultatele intermediare din Karatsuba
 */
Polynomial* combine_karatsuba(Polynomial *M1, Polynomial *M2,
                              Polynomial *M3, int k) {
    if (M1 == NULL || M2 == NULL || M3 == NULL) return NULL;

    int maxDeg = 0;
    if (M2->degree >= 0) maxDeg = M2->degree;
    if (M3->degree + k > maxDeg) maxDeg = M3->degree + k;
    if (M1->degree + 2*k > maxDeg) maxDeg = M1->degree + 2*k;

    Polynomial *result = create_polynomial(maxDeg);
    if (result == NULL) return NULL;

    // Adună: M2
    for (int i = 0; i <= M2->degree && i <= maxDeg; i++)
        result->coefficients[i] += M2->coefficients[i];

    // Adună: M3 * x^k
    for (int i = 0; i <= M3->degree && i + k <= maxDeg; i++)
        result->coefficients[i + k] += M3->coefficients[i];

    // Scade: (M1 + M2) * x^k
    for (int i = 0; i <= M1->degree && i + k <= maxDeg; i++)
        result->coefficients[i + k] -= M1->coefficients[i];
    for (int i = 0; i <= M2->degree && i + k <= maxDeg; i++)
        result->coefficients[i + k] -= M2->coefficients[i];

    // Adună: M1 * x^(2k)
    for (int i = 0; i <= M1->degree && i + 2*k <= maxDeg; i++)
        result->coefficients[i + 2*k] += M1->coefficients[i];

    return result;
}

/**
 * Karatsuba - Secvential
 *
 * Divide and Conquer: O(n^log2(3)) ≈ O(n^1.585)
 */
Polynomial* multiply_karatsuba_sequential(Polynomial *P, Polynomial *Q) {
    if (P == NULL || Q == NULL) return NULL;

    int n = (P->degree + 1 > Q->degree + 1) ? P->degree + 1 : Q->degree + 1;

    // Bază: pentru polinoame mici, folosim multiplicare clasică
    if (n <= 32) {
        return multiply_classical_sequential(P, Q);
    }

    // Padding la putere de 2
    int nPow2 = next_power_of_two(n);
    Polynomial *P_pad = pad_polynomial(P, nPow2);
    Polynomial *Q_pad = pad_polynomial(Q, nPow2);

    if (P_pad == NULL || Q_pad == NULL) {
        free_polynomial(P_pad);
        free_polynomial(Q_pad);
        return NULL;
    }

    int k = nPow2 / 2;

    // Împarte polinoamele
    Polynomial *P_low = get_low_part(P_pad, k);
    Polynomial *P_high = get_high_part(P_pad, k);
    Polynomial *Q_low = get_low_part(Q_pad, k);
    Polynomial *Q_high = get_high_part(Q_pad, k);

    if (P_low == NULL || P_high == NULL || Q_low == NULL || Q_high == NULL) {
        free_polynomial(P_pad);
        free_polynomial(Q_pad);
        free_polynomial(P_low);
        free_polynomial(P_high);
        free_polynomial(Q_low);
        free_polynomial(Q_high);
        return NULL;
    }

    // Calcule recursive
    Polynomial *M1 = multiply_karatsuba_sequential(P_high, Q_high);
    Polynomial *M2 = multiply_karatsuba_sequential(P_low, Q_low);

    Polynomial *P_sum = add_polynomials(P_high, P_low);
    Polynomial *Q_sum = add_polynomials(Q_high, Q_low);
    Polynomial *M3 = multiply_karatsuba_sequential(P_sum, Q_sum);

    if (M1 == NULL || M2 == NULL || M3 == NULL) {
        free_polynomial(P_pad);
        free_polynomial(Q_pad);
        free_polynomial(P_low);
        free_polynomial(P_high);
        free_polynomial(Q_low);
        free_polynomial(Q_high);
        free_polynomial(M1);
        free_polynomial(M2);
        free_polynomial(M3);
        free_polynomial(P_sum);
        free_polynomial(Q_sum);
        return NULL;
    }

    // Combinare
    Polynomial *result = combine_karatsuba(M1, M2, M3, k);

    // Cleanup
    free_polynomial(P_pad);
    free_polynomial(Q_pad);
    free_polynomial(P_low);
    free_polynomial(P_high);
    free_polynomial(Q_low);
    free_polynomial(Q_high);
    free_polynomial(M1);
    free_polynomial(M2);
    free_polynomial(M3);
    free_polynomial(P_sum);
    free_polynomial(Q_sum);

    return result;
}

// ============================================================================
// ALGORITM KARATSUBA - MPI (DISTRIBUȚIE PENTRU OPERAȚII RECURSIVE)
// ============================================================================

/**
 * Karatsuba cu MPI
 *
 * [MPI CURS PG. 20] MPI_Send / MPI_Recv - Comunicație point-to-point
 */
Polynomial* multiply_karatsuba_mpi(Polynomial *P, Polynomial *Q,
                                   int rank, int comm_size) {
    if (rank == 0 && (P == NULL || Q == NULL)) return NULL;

    int n = 0;
    if (rank == 0) {
        n = (P->degree + 1 > Q->degree + 1) ? P->degree + 1 : Q->degree + 1;
    }

    // Broadcast n la toți
    MPI_SAFE_CALL(MPI_Bcast(&n, 1, MPI_INT, 0, MPI_COMM_WORLD));

    // Bază: pentru polinoame mici, doar procesul 0 calculează
    if (n <= 32 || comm_size == 1) {
        Polynomial *result = NULL;
        if (rank == 0) {
            result = multiply_karatsuba_sequential(P, Q);
        }
        return result;
    }

    // Sincronizare inițială
    MPI_SAFE_CALL(MPI_Barrier(MPI_COMM_WORLD));

    // Padding
    int nPow2 = next_power_of_two(n);
    Polynomial *P_pad = NULL, *Q_pad = NULL;

    if (rank == 0) {
        P_pad = pad_polynomial(P, nPow2);
        Q_pad = pad_polynomial(Q, nPow2);
    } else {
        P_pad = create_polynomial(nPow2 - 1);
        Q_pad = create_polynomial(nPow2 - 1);
    }

    if (P_pad == NULL || Q_pad == NULL) {
        fprintf(stderr, "[Rank %d] ERROR: Failed to create padded polynomials\n", rank);
        MPI_Abort(MPI_COMM_WORLD, 1);
    }

    // [MPI CURS PG. 20] MPI_Bcast - Distribui polinoamele
    MPI_SAFE_CALL(MPI_Bcast(&nPow2, 1, MPI_INT, 0, MPI_COMM_WORLD));
    MPI_SAFE_CALL(MPI_Bcast(P_pad->coefficients, nPow2, MPI_DOUBLE, 0, MPI_COMM_WORLD));
    MPI_SAFE_CALL(MPI_Bcast(Q_pad->coefficients, nPow2, MPI_DOUBLE, 0, MPI_COMM_WORLD));

    int k = nPow2 / 2;

    // Împarte polinoamele
    Polynomial *P_low = get_low_part(P_pad, k);
    Polynomial *P_high = get_high_part(P_pad, k);
    Polynomial *Q_low = get_low_part(Q_pad, k);
    Polynomial *Q_high = get_high_part(Q_pad, k);

    if (P_low == NULL || P_high == NULL || Q_low == NULL || Q_high == NULL) {
        fprintf(stderr, "[Rank %d] ERROR: Failed to split polynomials\n", rank);
        MPI_Abort(MPI_COMM_WORLD, 1);
    }

    Polynomial *P_sum = add_polynomials(P_high, P_low);
    Polynomial *Q_sum = add_polynomials(Q_high, Q_low);

    if (P_sum == NULL || Q_sum == NULL) {
        fprintf(stderr, "[Rank %d] ERROR: Failed to add polynomials\n", rank);
        MPI_Abort(MPI_COMM_WORLD, 1);
    }

    // Distribuție: Fiecare proces calculează una din M1, M2, M3
    Polynomial *M1 = NULL, *M2 = NULL, *M3 = NULL;

    if (rank % 3 == 0) {
        M1 = multiply_karatsuba_sequential(P_high, Q_high);
    } else if (rank % 3 == 1) {
        M2 = multiply_karatsuba_sequential(P_low, Q_low);
    } else {
        M3 = multiply_karatsuba_sequential(P_sum, Q_sum);
    }

    // Trimitere la procesul 0
    if (rank == 0) {
        // M1 este deja calculat
        if (comm_size > 1 && M2 == NULL) {
            // Primește M2 de la procesul 1
            int M2_deg = 0;
            MPI_SAFE_CALL(MPI_Recv(&M2_deg, 1, MPI_INT, 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE));
            M2 = create_polynomial(M2_deg);
            if (M2 != NULL) {
                MPI_SAFE_CALL(MPI_Recv(M2->coefficients, M2_deg + 1, MPI_DOUBLE, 1, 0,
                                      MPI_COMM_WORLD, MPI_STATUS_IGNORE));
            }
        }
        if (comm_size > 2 && M3 == NULL) {
            // Primește M3 de la procesul 2
            int M3_deg = 0;
            MPI_SAFE_CALL(MPI_Recv(&M3_deg, 1, MPI_INT, 2, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE));
            M3 = create_polynomial(M3_deg);
            if (M3 != NULL) {
                MPI_SAFE_CALL(MPI_Recv(M3->coefficients, M3_deg + 1, MPI_DOUBLE, 2, 0,
                                      MPI_COMM_WORLD, MPI_STATUS_IGNORE));
            }
        }
    } else if (rank == 1 && comm_size > 1 && M2 != NULL) {
        // [MPI CURS PG. 20] MPI_Send - Trimite M2
        MPI_SAFE_CALL(MPI_Send(&M2->degree, 1, MPI_INT, 0, 0, MPI_COMM_WORLD));
        MPI_SAFE_CALL(MPI_Send(M2->coefficients, M2->degree + 1, MPI_DOUBLE, 0, 0, MPI_COMM_WORLD));
    } else if (rank == 2 && comm_size > 2 && M3 != NULL) {
        // [MPI CURS PG. 20] MPI_Send - Trimite M3
        MPI_SAFE_CALL(MPI_Send(&M3->degree, 1, MPI_INT, 0, 0, MPI_COMM_WORLD));
        MPI_SAFE_CALL(MPI_Send(M3->coefficients, M3->degree + 1, MPI_DOUBLE, 0, 0, MPI_COMM_WORLD));
    }

    // Combinare - DOAR PROCESUL 0
    Polynomial *result = NULL;
    if (rank == 0) {
        if (M1 != NULL && M2 != NULL && M3 != NULL) {
            result = combine_karatsuba(M1, M2, M3, k);
        } else {
            result = create_polynomial(0);
        }
    }

    // Cleanup - IMPORTANT! Fiecare proces TREBUIE să-și elibereze memoria!
    free_polynomial(P_pad);
    free_polynomial(Q_pad);
    free_polynomial(P_low);
    free_polynomial(P_high);
    free_polynomial(Q_low);
    free_polynomial(Q_high);
    free_polynomial(P_sum);
    free_polynomial(Q_sum);
    if (M1) free_polynomial(M1);
    if (M2) free_polynomial(M2);
    if (M3) free_polynomial(M3);

    return result;
}

// ============================================================================
// FUNCȚII PRINCIPALE - BENCHMARK
// ============================================================================

/**
 * Măsoară timp în secunde cu precizie
 */
double get_time() {
    return (double)clock() / CLOCKS_PER_SEC;
}

/**
 * Main program - VERSIUNEA CORECTĂ PENTRU WINDOWS MPI
 */
int main(int argc, char **argv) {
    // ========== INIȚIALIZARE MPI ==========
    // [MPI CURS PG. 12]
    // MPI_Init: Inițializează mediul MPI paralel
    // - Trebuie apelat ÎNAINTE de oricare alt apel MPI

    MPI_SAFE_CALL(MPI_Init(&argc, &argv));

    // ========== OBȚINERE RANK ȘI SIZE ==========
    // [MPI CURS PG. 14]
    // MPI_Comm_rank: Obține rang-ul (id-ul) acestui proces în MPI_COMM_WORLD
    // MPI_Comm_size: Obține numărul total de procese

    int rank, comm_size;
    MPI_SAFE_CALL(MPI_Comm_rank(MPI_COMM_WORLD, &rank));
    MPI_SAFE_CALL(MPI_Comm_size(MPI_COMM_WORLD, &comm_size));

    // Print info doar de procesul 0 (master)
    if (rank == 0) {
        printf("\n");
        printf("========== POLYNOMIAL MULTIPLICATION WITH MPI ==========\n");
        printf("Procese actuale: %d\n", comm_size);
        printf("=========================================================\n\n");
        fflush(stdout);
    }

    // Test sizes
    int test_degrees[] = {32, 64, 128, 256, 512, 1024};
    int num_tests = sizeof(test_degrees) / sizeof(test_degrees[0]);

    // ========== BENCHMARK LOOP ==========
    for (int test_idx = 0; test_idx < num_tests; test_idx++) {
        int degree = test_degrees[test_idx];

        if (rank == 0) {
            printf("\n--- Test cu polinoame de grad %d ---\n", degree);
            fflush(stdout);
        }

        // Generează polinoame random (doar procesul 0)
        Polynomial *P = NULL, *Q = NULL;
        if (rank == 0) {
            P = generate_random_polynomial(degree, 42 + test_idx);
            Q = generate_random_polynomial(degree, 100 + test_idx);
        }

        // ===== TEST 1: Clasic Sequential =====
        double time_start = get_time();
        Polynomial *result1 = NULL;
        if (rank == 0) {
            result1 = multiply_classical_sequential(P, Q);
        }
        double time_classical_seq = get_time() - time_start;

        if (rank == 0) {
            printf("1. Clasic Sequential:  %.6f sec\n", time_classical_seq);
            fflush(stdout);
        }

        // ===== TEST 2: Clasic MPI =====
        // [MPI CURS PG. 12-13] MPI_Barrier: Sincronizare
        MPI_SAFE_CALL(MPI_Barrier(MPI_COMM_WORLD));

        time_start = get_time();
        Polynomial *result2 = multiply_classical_mpi(P, Q, rank, comm_size);
        double time_classical_mpi = get_time() - time_start;

        if (rank == 0) {
            printf("2. Clasic MPI:         %.6f sec\n", time_classical_mpi);

            // Validare
            if (result2 != NULL && polynomials_equal(result1, result2, 1e-6)) {
                printf("    Rezultat corect!\n");
            } else if (result2 != NULL) {
                printf("    EROARE: Rezultate diferite!\n");
            }
            fflush(stdout);
        }

        // ===== TEST 3: Karatsuba Sequential =====
        MPI_SAFE_CALL(MPI_Barrier(MPI_COMM_WORLD));

        time_start = get_time();
        Polynomial *result3 = NULL;
        if (rank == 0) {
            result3 = multiply_karatsuba_sequential(P, Q);
        }
        double time_karatsuba_seq = get_time() - time_start;

        if (rank == 0) {
            printf("3. Karatsuba Sequential: %.6f sec\n", time_karatsuba_seq);

            // Validare
            if (result3 != NULL && polynomials_equal(result1, result3, 1e-6)) {
                printf("    Rezultat corect!\n");
            } else if (result3 != NULL) {
                printf("    EROARE: Rezultate diferite!\n");
            }
            fflush(stdout);
        }

        // ===== TEST 4: Karatsuba MPI =====
        MPI_SAFE_CALL(MPI_Barrier(MPI_COMM_WORLD));

        time_start = get_time();
        Polynomial *result4 = multiply_karatsuba_mpi(P, Q, rank, comm_size);
        double time_karatsuba_mpi = get_time() - time_start;

        if (rank == 0) {
            printf("4. Karatsuba MPI:      %.6f sec\n", time_karatsuba_mpi);

            // Validare
            if (result4 != NULL && polynomials_equal(result1, result4, 1e-6)) {
                printf("    Rezultat corect!\n");
            } else if (result4 != NULL) {
                printf("    EROARE: Rezultate diferite!\n");
            }

            // Speedup
            printf("\n   Speedup (Clasic MPI vs Sequential): %.2fx\n",
                   (time_classical_seq > 0) ? time_classical_seq / time_classical_mpi : 0);
            printf("   Speedup (Karatsuba MPI vs Sequential): %.2fx\n",
                   (time_karatsuba_seq > 0) ? time_karatsuba_seq / time_karatsuba_mpi : 0);
            fflush(stdout);
        }

        // Cleanup - IMPORTANT! Fiecare proces elibereaza
        if (rank == 0) {
            if (P) free_polynomial(P);
            if (Q) free_polynomial(Q);
            if (result1) free_polynomial(result1);
            if (result2) free_polynomial(result2);
            if (result3) free_polynomial(result3);
            if (result4) free_polynomial(result4);
        }
    }

    if (rank == 0) {
        printf("\n========== FINALIZARE TEST ==========\n\n");
        fflush(stdout);
    }

    // ========== FINALIZARE MPI - CRITICAL! ==========
    // [MPI CURS PG. 12]
    // MPI_Finalize: Finalizează mediul MPI
    // - TREBUIE apelat de FIECARE proces la SFÂRŞIT!
    // - După MPI_Finalize, nu se mai pot face apeluri MPI

    fprintf(stdout, "[Rank %d] Calling MPI_Finalize...\n", rank);
    fflush(stdout);

    MPI_SAFE_CALL(MPI_Finalize());

    fprintf(stdout, "[Rank %d] MPI_Finalize completed successfully!\n", rank);
    fflush(stdout);

    return 0;
}
