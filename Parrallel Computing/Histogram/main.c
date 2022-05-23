#include <stdio.h>
#include <string.h>
#include <malloc.h>
#include "mpi.h"

int min(int x, int y) {
    return x < y ? x : y;
}

int max(int x, int y) {
    return x > y ? x : y;
}

int Ceil(double x) {
    int y = (int) x;
    return x == (double) y ? y : y + 1;
}

int main(int argc, char *argv[]) {
    int numprocs, interval, size, rank, i, j, age, mn, mx, len, threadnum;
    FILE *fptr;
    int *data, *rec;
    int *bars, numbars, *rbuf;

    MPI_Init(&argc, &argv);

    MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);

    if (rank == 0) {
        printf("Enter num of bars\n");
        scanf("%d", &numbars);
        printf("Enter num of points\n");
        scanf("%d", &size);
        printf("Enter num of Threads\n");
        scanf("%d", &threadnum);

        if (size % numprocs != 0)
            size = ((size / numprocs) + 1) * numprocs;
        data = malloc(size * sizeof(int));
        if ((fptr = fopen("dataset.txt", "r")) == NULL) {
            printf("Error! opening file\n");
            // Program exits if the file pointer returns NULL.
            MPI_Finalize();
            return 0;
        } else {
            mn = 1e5;
            mx = 0;
            for (i = 0; fscanf(fptr, "%d", &age) != EOF && i < size; ++i) {
                data[i] = age;
                mn = min(mn, age);
                mx = max(mx, age);
            }

            for (; i < size; ++i) data[i] = -1;
            interval = Ceil((mx - mn * 1.0) / numbars * 1.0);
            printf("inter %d\n", interval);


        }
    }
    MPI_Bcast(&size, 1, MPI_INT, 0, MPI_COMM_WORLD);
    MPI_Bcast(&interval, 1, MPI_INT, 0, MPI_COMM_WORLD);
    MPI_Bcast(&numbars, 1, MPI_INT, 0, MPI_COMM_WORLD);
    MPI_Bcast(&threadnum, 1, MPI_INT, 0, MPI_COMM_WORLD);
    bars = malloc(numbars);
    len = size / numprocs;
    rec = malloc(len * sizeof(int));
    MPI_Scatter(data, len, MPI_INT, rec, len, MPI_INT, 0,
                MPI_COMM_WORLD);
#pragma omp parallel num_threads(threadnum) shared(interval, bars, len, rec, rank) private(i, j)
    {
#pragma omp for schedule(static)
        for (i = 0; i < numbars; ++i) {
            bars[i] = 0;
        }

#pragma omp for schedule(static)
        for (i = 0; i < len; ++i) {
            if (rec[i] == -1)continue;
            j = (rec[i] - 1) / interval, 0;
            bars[j]++;
        }

    }

    if (rank == 0) {
        rbuf = malloc(numprocs * numbars * sizeof(int));
    }

    MPI_Gather(bars, numbars, MPI_INT, rbuf, numbars, MPI_INT, 0,
               MPI_COMM_WORLD);

    if (rank == 0) {
#pragma omp parallel shared(rbuf, numbars, numprocs) private(i)
        {
#pragma omp for schedule(static)
            for (i = numbars; i < numprocs * numbars; ++i) {
                rbuf[i % numbars] += rbuf[i];
            }

        }


        int st = 0, end = interval;
        for (i = 0; i < numbars; ++i) {
            printf("The range start with %d, end with %d, with count %d\n", st, end, rbuf[i]);
            st += interval;
            end += interval;
        }

    }



/*
    for (i = 0; i < len; ++i) {
        printf("i'm process %d and i have %d and idx %d\n", rank, rec[i],i+rank*len);
    }*/
    MPI_Finalize();
    return 0;
}
