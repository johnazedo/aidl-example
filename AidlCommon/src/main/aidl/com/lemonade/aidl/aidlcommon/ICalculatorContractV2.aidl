package com.lemonade.aidl.aidlcommon;

interface ICalculatorContractV2 {
    int add(int num1, int num2);
    int times(int num1, int num2);
    int sub(int num1, int num2);
    int div(int num1, int num2, int errorValue);
    int mod(int num1, int num2);
}