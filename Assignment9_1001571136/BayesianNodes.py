class BayesianNodes:
    gvProb = {}
    B = 0.001
    E = 0.002
    gvProb['M'] = {'AT':0.70,'AF':0.01}
    gvProb['JT'] = {'AT':0.90,'AF':0.05}
    gvProb['AT'] = {'BT_ET':0.95,'BT_EF':0.94,'BF_ET':0.29,'BF_EF':0.001}


    def computeProbability(self, burglary, earthquake, alarm, john, mary, conditions):
        johnCalls = 0.00
        maryCalls = 0.00
        Alarm_p = 0.00
        probability = 1
        p_burglary = 0.00
        p_earthquake = 0.00
        denominator = 1.00

        if earthquake:
            p_earthquake = self.E
        else:
            p_earthquake = 1 - self.E

        if burglary:
            p_burglary = self.B
        else:
            p_burglary = 1 - self.B

        if alarm:
            if john:
                johnCalls = self.gvProb['JT']['AT']
            else:
                johnCalls = 1 - self.gvProb['JT']['AT']
            if mary:
                maryCalls = self.givenrobabilities['MT']['AT']
            else:

                maryCalls = 1 - self.gvProb['MT']['AT']
        else:
            if john:
                johnCalls = self.gvProb['JT']['AF']
            else:
                johnCalls = 1 - self.gvProb['JT']['AF']
            if mary:
                maryCalls = self.gvProb['MT']['AF']
            else:
                maryCalls = 1 - self.gvProb['MT']['AF']

        if burglary and earthquake:
            if alarm:
                Alarm_p = self.gvProb['AT']['BT_ET']
            else:
                Alarm_p = 1 - self.gvProb['AT']['BT_ET']
        if (not burglary) and earthquake:
            if alarm:
                Alarm_p = self.gvProb['AT']['BF_ET']
            else:
                Alarm_p = 1 - self.gvProb['AT']['BF_ET']
        if burglary and (not earthquake):
            if alarm:
                Alarm_p = self.gvProb['AT']['BT_EF']
            else:
                Alarm_p = 1 - self.gvProb['AT']['BT_EF']
        if (not burglary) and (not earthquake):
            if alarm:
                Alarm_p = self.gvProb['AT']['BF_EF']
            else:
                Alarm_p = 1 - self.gvProb['AT']['BF_EF']

        for condition1 in conditions:
            if condition1 == 'B':
                denominator*=p_burglary
            if condition1 == 'E':
                denominator*=p_earthquake
            if condition1 == 'A':
                denominator*=Alarm_p
            if condition1 == 'J':
                denominator*=johnCalls
            if condition1 == 'M':
                denominator*=maryCalls

        num = (johnCalls*maryCalls*Alarm_p*p_burglary*p_earthquake)
        return num/den
