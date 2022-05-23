#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include<mpi.h>

int main(int argc, char *argv[]) {

    int my_rank;        /* rank of process	*/
    int p;            /* number of process	*/
    int id;             //id
    int i;
    int rem;         //remaining data.
    int grade;              //grade
    int psum;             //sum of each process
    int sum;            //Total sum
    int source;        /* rank of sender	*/
    int dest;        /* rank of reciever	*/
    int tag = 0;/* tag for messages	*/

    int datasize;       //size of the data.
    int rec_data[100000][2];    /* storage for message	*/
    int data[100000][2];       //my data.
    int st;                //start part of my data.
    int itr;               //number of data for each process

    MPI_Status status;    /* return status for 	*/
    FILE *fptr;          //pointer that point to the file.

    /* Start up MPI */
    MPI_Init(&argc, &argv);
    /* Find out process rank */
    MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);
    /* Find out number of process */
    MPI_Comm_size(MPI_COMM_WORLD, &p);

    if (my_rank != 0) {
        source = 0;

        //Receive the size of data from the source.
        MPI_Recv(&itr, 1, MPI_INT, source, tag, MPI_COMM_WORLD, &status);
        //Receive my part of data from the source.
        MPI_Recv(&rec_data, itr * 2, MPI_INT, source, tag, MPI_COMM_WORLD, &status);

        psum = 0;
        for (i = 0; i < itr; i++) {
            if (rec_data[i][1] >= 60) {
                psum++;
                printf("%d Passed The Exam\n", rec_data[i][0]);
            } else
                printf("%d Failed. Please repeat The Exam\n", rec_data[i][0]);

        }

        dest = 0;
        //send process's sum to process 0.
        MPI_Send(&psum, 1, MPI_INT, dest, tag, MPI_COMM_WORLD);


    } else {
        //open the File
        if ((fptr = fopen("stud.txt", "r")) == NULL) {
            printf("Error! opening file\n");

            // Program exits if the file pointer returns NULL.
            MPI_Finalize();
            exit(1);
        }

        //Read 100 students from the file.
        datasize = 0;
        for (i = 0; fscanf(fptr, "%d %d", &id, &grade) != EOF; ++i) {
            data[i][0] = id;
            data[i][1] = grade;
            datasize++;
        }

        //each process will take itr students.
        itr = datasize / p;
        if (p > 1) {

            //send partial data to each process
            for (dest = 1; dest < p - 1; dest++) {
                st = dest * itr;
                MPI_Send(&itr, 1, MPI_INT, dest, tag, MPI_COMM_WORLD);

                MPI_Send(&data[st], itr * 2, MPI_INT, dest, tag, MPI_COMM_WORLD);
            }

            //st for last process
            st = dest * itr;

            //give the remaining students to the last process.
            rem = itr + datasize % p;

            //Send rem for the last process.
            MPI_Send(&rem, 1, MPI_INT, dest, tag, MPI_COMM_WORLD);

            //send partial data for the last process.
            MPI_Send(&data[st], rem * 2, MPI_INT, dest, tag, MPI_COMM_WORLD);
        }
        //process 0 take first itr part
        sum = 0;
        for (i = 0; i < itr; i++) {
            if (data[i][1] >= 60) {
                sum++;
                printf("%d Passed The Exam\n", data[i][0]);
            } else
                printf("%d Failed. Please repeat The Exam\n", data[i][0]);
        }

        //receive the sum from processes
        for (i = 1; i < p; i++) {
            MPI_Recv(&psum, 1, MPI_INT, MPI_ANY_SOURCE, tag, MPI_COMM_WORLD, &status);
            sum += psum;
        }
        printf("Total number of students passed the exam is %d of %d \n", sum, datasize);

        //Close the file.
        fclose(fptr);


    }

/* shutdown MPI */
    MPI_Finalize();

    return 0;
}