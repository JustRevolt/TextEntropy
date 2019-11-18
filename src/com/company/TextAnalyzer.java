package com.company;

import java.math.BigDecimal;
import java.math.RoundingMode;

class TextAnalyzer {
    private byte[] text;
    private int textLength;
    private Double[] numberOfSymbols =new Double[28];
    private Double[] probabilityOfSymbols = new Double[28];
    private Double[] entropyOfSymbols = new Double[28];
    private double textEntropy = 0;

    private int[][] numberOfPairs =new int[28][28];

    private double[][] probabilityOfPairs = new double[28][28];
    private double textEntropyWithPairs = 0;

    TextAnalyzer(byte[] text){
        this.text = text;
        textLength = text.length;
    }

    int getTextLength() {
        return textLength;
    }

    Double[] getNumberOfSymbols() {
        for (int i = 0; i<numberOfSymbols.length; i++){
            numberOfSymbols[i] = new BigDecimal(numberOfSymbols[i]).setScale(0, RoundingMode.UP).doubleValue();
        }
        return numberOfSymbols;
    }

    Double[] getProbabilityOfSymbols() {
        for (int i = 0; i<probabilityOfSymbols.length; i++){
            probabilityOfSymbols[i] = new BigDecimal(probabilityOfSymbols[i]).setScale(4, RoundingMode.UP).doubleValue();
        }
        return probabilityOfSymbols;
    }

    Double[] getEntropyOfSymbols() {
        for (int i = 0; i<entropyOfSymbols.length; i++){
            if(entropyOfSymbols[i] != Double.POSITIVE_INFINITY)
                entropyOfSymbols[i] = new BigDecimal(entropyOfSymbols[i]).setScale(4, RoundingMode.UP).doubleValue();
        }
        return entropyOfSymbols;
    }

    double getTextEntropy() {
        textEntropy = new BigDecimal(textEntropy).setScale(4, RoundingMode.UP).doubleValue();
        return textEntropy;
    }

    double getTextEntropyWithPairs() {
        textEntropyWithPairs = new BigDecimal(textEntropyWithPairs).setScale(4, RoundingMode.UP).doubleValue();
        return textEntropyWithPairs;
    }

    void analyse(){
        symbolAnalyse();
        symbolsEntropyCalculation();
        textEntropyCalculation();
        pairsAnalyse();
        entropyWithPairCalculation();
    }

    private void symbolAnalyse(){
        for (int i=0;i<numberOfSymbols.length;i++) {
            numberOfSymbols[i]=0.0;
            probabilityOfSymbols[i]=0.0;
        }
        for (byte symbol:text) {
            if (((int) symbol == 32)|((int) symbol>=65 & (int) symbol<=90)|((int) symbol>=97 & (int) symbol<=122)){
                numberOfSymbols[(int) symbol % 32]+=1;
                probabilityOfSymbols[(int) symbol % 32]+= (double) 1/textLength;
            } else {
                numberOfSymbols[27]+=1;
                probabilityOfSymbols[27]+=(double) 1/textLength;
            }
        }
    }

    private void symbolsEntropyCalculation(){
        int i = 0;
        for (double probability:probabilityOfSymbols) {
            entropyOfSymbols[i] = Math.log((double) 1/probability);
            i+=1;
        }
    }

    private void textEntropyCalculation(){
        for (int i = 0; i<28; i++) {
            if (entropyOfSymbols[i] != Double.POSITIVE_INFINITY)
            textEntropy += probabilityOfSymbols[i]* entropyOfSymbols[i];
        }
    }

    private void pairsAnalyse(){
        for (int i = 0; i<numberOfPairs.length; i++) {
            for (int j = 0; j<numberOfPairs[i].length; j++) {
                numberOfPairs[i][j]=0;
                probabilityOfPairs[i][j]=0;
            }
        }

        for (int n = 1; n<textLength; n++){
            int i=27;
            int j=27;
            if (((int) text[n] == 32)|((int) text[n] >=65 & (int) text[n] <=90)|((int) text[n] >=97 & (int) text[n] <=122)){
                i = (int) text[n] % 32;
            }
            if (((int) text[n-1] == 32)|((int) text[n-1] >=65 & (int) text[n-1] <=90)|((int) text[n-1] >=97 & (int) text[n-1] <=122)) {
                j = (int) text[n-1] % 32;
            }
            numberOfPairs[j][i]+=1;
            probabilityOfPairs[j][i]+=(double) 1/(textLength-1);
        }
    }

    private void entropyWithPairCalculation() {
        for (int j = 0; j<probabilityOfPairs.length; j++){
            for (int i = 0; i<probabilityOfPairs[j].length; i++){
                if (probabilityOfPairs[j][i] != 0) {
                    textEntropyWithPairs -= probabilityOfPairs[j][i] * probabilityOfSymbols[j] * Math.log(probabilityOfPairs[j][i]);
                }
            }
        }

    }
}
