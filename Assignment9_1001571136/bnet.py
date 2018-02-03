import sys
from copy import *
from BayesianNodes import *


def createTables(probTable,input_condition):
    if input_condition.count(None) != 0:
        noneIndex = input_condition.index(None)
        t = deepcopy(input_condition)
        t[noneIndex] = True
        f = deepcopy(input_condition)
        f[noneIndex] = False
        createTables(probTable,t)
        createTables(probTable,f)
        return probTable
    else:
        probTable.append(input_condition)
        return probTable

def main(argv):

    input_arr = argv[1:]
    condition = [];
    burglary = 0.0;
    earthquake = 0.0;
    alarm = 0.0;
    johnCalls = 0.0;
    maryCalls = 0.0;
    count_b=0.0;
    print_prob=None;
    print_string=str(argv[1:])
    for inputField in input_arr:

        firstChar = inputField[0].upper();
        secondChar = inputField[1];
        if firstChar == 'B' and secondChar == 't':
            burglary = True
            print_prob="(Burglary=true)";
            print_string.replace("Bt", "Burglary=true")

        elif firstChar == 'B' and secondChar == 'f':
            burglary = False
            print_prob="(Burglary=true)";
            print_string.replace("Bf", "Burglary=false")

        if firstChar == 'E' and secondChar == 't':
            earthquake = True
            print_string.replace("Et", "earthquake=true")

        elif firstChar == 'E' and secondChar == 'f':
            earthquake = False
            print_prob="(earthquake=false)";
            print_string.replace("Ef", "earthquake=false")

        if firstChar == 'A' and secondChar == 't':
            alarm = True
            print_prob="(Alarm=true)";
            print_string.replace("At", "Alarm=true")

        elif firstChar == 'A' and secondChar == 'f':
            alarm = False
            print_prob="(Alarm=false)";
            print_string.replace("Af", "Alarm=false")

        if firstChar == 'J' and secondChar == 't':
            johnCalls = True
            print_prob="(johnCalls=true)";
            print_string.replace("Jt", "johnCalls=true")

        elif firstChar == 'J' and secondChar == 'f':
            johnCalls = False
            print_prob="(johnCalls=false)";
            print_string.replace("Jf", "johnCalls=false")


        if firstChar == 'M' and secondChar == 't':
            maryCalls = True
            print_prob="(maryCalls=true)";
            print_string.replace("Mt", "maryCalls=true")

        elif firstChar == 'M' and secondChar == 'f':
            maryCalls = False
            print_prob="(maryCalls=false)";
            print_string.replace("Mf", "maryCalls=false")

    conIndex = 0
    if input_arr.count('given'):
        conIndex = input_arr.index('given')
        for j in range(conIndex+1,len(input_arr)):
            condition.append(input_arr[j][0])
    input_condition = [burglary,earthquake,alarm,johnCalls,maryCalls];
    denominators = condition;

    bn = BayesianNodes()
    tables = createTables([],input_condition)

    probability = 0.00

    for values in tables:
        probability += bn.computeProbability(values[0],values[1],values[2],values[3],values[4],condition)



    print 'P('+print_string+') is '+str('%f'%probability)

if __name__ == '__main__':
    main(sys.argv)
