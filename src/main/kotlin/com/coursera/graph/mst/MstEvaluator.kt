package com.coursera.graph.mst

import com.coursera.graph.Edge

interface MstEvaluator {

    fun evaluate(): List<Edge>
}