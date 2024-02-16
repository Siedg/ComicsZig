package br.com.comicszig

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.rules.RuleChain

val instantLiveDataAndCoroutineRules: RuleChain
    get() = RuleChain
        .outerRule(TestCoroutineRule())
        .around(InstantTaskExecutorRule())