package com.lemonade.aidlcommon;

import com.lemonade.aidl.aidlcommmon.CalculatorServiceResult;
import com.lemonade.aidl.aidlcommmon.Numbers;

interface ICalculatorContractV3 {
    CalculatorServiceResult add(Numbers numbers);
    CalculatorServiceResult times(Numbers numbers);
    CalculatorServiceResult sub(Numbers numbers);
    CalculatorServiceResult div(Numbers numbers);
    CalculatorServiceResult mod(Numbers numbers);
}