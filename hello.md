Clojure is a programming language for the JVM. I like Clojure for its simplicity and power.
For example clojure has a function called juxt which can be used like this

	(def +- (juxt + -))

Clojure also has good support for concurency through stm and the
functional style of programming.

*Functional Programming* is programming with functions preferably pure ones.
Clojure has good support for functional programming because it has a shorter
name for **lambda** namely **fn**. Another reason is the #() which is short for fn. These forms are used like this.

	(map #(+ 10 %) [1 2 3 4 5 6])
	;=> (11 12 13 14 15 16)
	(map (fn [x] (+ x 10)) [1 2 3 4 5 6])
	;=> (11 12 13 14 15 16)
	(map (partial + 10) [1 2 3 4 5 6])
	;=> (11 12 13 14 15 16)
	
