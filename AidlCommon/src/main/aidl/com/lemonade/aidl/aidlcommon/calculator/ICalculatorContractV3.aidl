package com.lemonade.aidl.aidlcommon.calculator;

import com.lemonade.aidl.aidlcommon.calculator.CalculatorServiceResult;
import com.lemonade.aidl.aidlcommon.calculator.Numbers;

parcelable Numbers;
parcelable CalculatorServiceResult;

interface ICalculatorContractV3 {
    CalculatorServiceResult add(in Numbers numbers);
    CalculatorServiceResult times(in Numbers numbers);
    CalculatorServiceResult sub(in Numbers numbers);
    CalculatorServiceResult div(in Numbers numbers);
    CalculatorServiceResult mod(in Numbers numbers);
}