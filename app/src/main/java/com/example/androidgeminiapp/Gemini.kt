package com.example.androidgeminiapp

import com.google.ai.client.generativeai.GenerativeModel

suspend fun Gem(text: String): String {
    val generativeModel = GenerativeModel(
        // For text-only input, use the gemini-pro model
        modelName = "gemini-pro",
        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
        apiKey = "AIzaSyCk0eByBcTXAj3T-eCF9iEMMeK-WR4yrkg"
    )

    return generativeModel.generateContent(text).text.toString()

}
