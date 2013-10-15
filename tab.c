/* Tabulate Borda Count votes. */

#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <stdlib.h>

struct vote {
    char candidate;
    int tally;
} votes[6];
char line[9];

int tally_order(const void *v1p, const void *v2p) {
    const struct vote *v1 = v1p;
    const struct vote *v2 = v2p;
    return v2->tally - v1->tally;
}

int main(int argc, char **argv) {
    if (argc != 1) {
        fprintf(stderr, "tab: usage: tab < votes-file\n");
        return 1;
    }
    for (int i = 0; i < 6; i++) {
        votes[i].candidate = i + 'a';
        votes[i].tally = 0;   /* XXX not strictly necessary */
    }
    while (1) {
        char *lineptr = fgets(line, sizeof(line), stdin);
        if (!lineptr)
            break;
        if (strlen(lineptr) != 7) {
            fprintf(stderr, "tab: bad line length\n");
            return 1;
        }
        for (int i = 0; i < 6; i++) {
            if (line[i] == '-')
                continue;
            if (line[i] < 'a' || line[i] > 'f') {
                fprintf(stderr, "tab: bogus candidate\n");
                return 1;
            }
            votes[line[i] - 'a'].tally += 6 - i;
        }
    }
    if (errno != 0) {
        perror("tab: read error");
        return 1;
    }
    qsort(votes, 6, sizeof(votes[0]), tally_order);
    for (int i = 0; i < 6; i++)
        printf("%c: %d\n", votes[i].candidate, votes[i].tally);
    return 0;
}
