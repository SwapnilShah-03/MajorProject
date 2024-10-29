import sys
def calculate_polarity(sentence):
    negation_weight = 0
    affirmation_weight = 0
    frequency_modifier = 1  # Default, neutral modifier
    double_negation = False
    has_negation = False
    has_affirmation = False

    # Expanded list of negation and affirmation words/phrases
    negation_words = [
    'no', 'not', 'never', 'hardly', 'barely', 'seldom', 'none', 'nowhere', "didn't", "doesn't", "won't",
    "couldn't", "wouldn't", "can't", "don't", "isn't", "wasn't", "weren't", "ain't", 'without', 'no one',
    'by no means', 'not at all', 'rarely', 'almost never', 'scarcely', 'neither', 'nor', 'nothing',
    'nobody', 'nonetheless', 'no longer', 'noway', 'under no circumstances', 'little', 'lack', 'few',
    'without fail', 'unlike', 'hardly ever', 'impossible', 'insufficient', 'unwilling', 'doubt', 'nevermore'
    ]

    
    affirmation_words = [
    'yes', 'indeed', 'absolutely', 'certainly', 'definitely', 'sure', 'affirmative', 'yep', 'yeah', 
    'without a doubt', 'of course', 'undoubtedly', 'good', 'yessir', 'exactly', 'surely', 'truly',
    'positively', 'clearly', 'for sure', 'granted', 'naturally', 'right', 'fine', 'correct', 'sure thing',
    'yup', 'roger', 'assuredly', 'totally', 'clearly', 'okay', 'alright', 'amen'
    ]

    
    frequency_modifiers = [
    'sometimes', 'rare', 'occasionally', 'frequently', 'often', 'regularly', 'seldom', 'hardly ever', 'almost never', 
    'every now and then', 'usually', 'periodically', 'sporadically', 'infrequent', 'mostly', 'from time to time',
    'annually', 'daily', 'hourly', 'weekly', 'monthly', 'constantly', 'always', 'intermittently', 'recurrently',
    'bi-weekly', 'semi-annually', 'seasonally', 'once in a while', 'at times', 'generally', 'mostly', 'frequent',
    'repeatedly', 'rarely', 'habitually', 'every so often', 'customarily', 'now and again', 'ever'
    ]


    # Tokenize and process the sentence
    tokens = sentence.lower().split()
    
    for i, token in enumerate(tokens):
        # Detect negation
        if token in negation_words:
            has_negation = True
            negation_weight = 70  # Assign base high weight to negation

        # Double negation detection: If two negations are detected, treat it as affirmation
        if i > 0 and tokens[i-1] in negation_words and token in negation_words:
            double_negation = True
            negation_weight = 0  # In case of double negation, reset negation weight
        
        # Detect affirmation
        if token in affirmation_words:
            has_affirmation = True
            affirmation_weight = 30  # Assign base weight to affirmation
        
        # Frequency modifiers (e.g., "sometimes," "rare")
        if token in frequency_modifiers:
            if token in ['rare', 'occasionally', 'seldom', 'hardly ever', 'almost never']:
                frequency_modifier = 0.5  # Decrease weight for less frequent occurrences
            elif token in ['frequently', 'often', 'usually', 'regularly', 'mostly']:
                frequency_modifier = 1.5  # Increase weight for frequent occurrences
    
    # Adjust affirmation weight based on frequency modifiers
    final_affirmation = affirmation_weight * frequency_modifier

    # Handle double negation as a strong affirmation
    if double_negation:
        final_affirmation = 70  # Treat double negation as an affirmation
        final_negation = 30
    else:
        # If both negation and affirmation are present, distribute weights
        if has_negation and has_affirmation:
            final_negation = (negation_weight * (1 / frequency_modifier))  # Adjust negation by the inverse frequency modifier
            final_affirmation = 100 - final_negation
        else:
            # Ensure weights sum to 100
            final_negation = 100 - final_affirmation
    return final_negation, final_affirmation



if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("No file path provided!")
        sys.exit(1)
    s = calculate_polarity(sys.argv[1])
    # s = calculate_polarity("I am good but sometimes feel down")
    print(str(s[1]))
 