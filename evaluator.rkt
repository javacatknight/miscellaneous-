#lang racket
(provide evaluate simplify)
(require rackunit)
(require rackunit/text-ui)
(define unary-op first)
(define operand second)
(define binary-op second)
(define left first)
(define right third)

;;for unaries, it's unary-op and operand.
;;for binaries, its left binary-op right
(define (variable? v) (not (or (boolean? v) (list? v)))) ;;variable is a non list/non bool
(define (unary? expr) (and (list? expr) (= 2 (length expr)))) ;;expression is unary if length 2 and list, but (2 3) would beurnary 
(define (binary? expr) (and (list? expr) (= 3 (length expr))))
(define (bracketed? expr) (and (list? expr) (= 1 (length expr))))

; (value-of k values) -> any
; k: any
; values: list of key/value pairs
; If k is a key in values, then return the value for that key.
; Otherwise, return k itself.
(define (value-of k values)
  (dict-ref values k k))
;;(dict-ref #hash((a . "apple") (b . "beer")) 'a)
;;"apple"
;;this is for the context thing shem entione,d ie evaluating values

(define ops
  (list (cons 'and (λ (a b) (and a b)))
        (cons 'or (λ (a b) (or a b)))
        (cons 'not not)
        (cons 'implies (λ (a b) (or (not a) b)))
        ))
          
                                      
(define simplifiers
  (list (cons 'and
              (lambda (a b context)
                (let ([y (simplify b context)]
                      [x (simplify a context)])
                  (cond [(or (equal? x #f) (equal? y #f)) #f] 
                        [(equal? x #t) y]           
                        [(equal? y #t) x]
                        [else (list x 'and y)]))))
        

        (cons 'or
              (lambda (a b context) 
                                     (let ([x (simplify a context)]
                                           [y (simplify b context)])

                                       (cond [(or (equal? x #t) (equal? y #t)) #t]
                                             [(equal? x #f) y]
                                             [(equal? y #f) x]
                                             [else (list x 'or y)]
                                             )
                                       )))
        (cons 'not
              (lambda (a context)
                (if (unary? a)
                    (simplify (operand a) context) ;;(not(not x)) = x
                    (let ([x (simplify a context)])
                      (if (boolean? x)
                          (not x)
                          (list 'not x))
                      )
                    )
                ))

        (cons 'implies
              (lambda (a b context) 
                                     (let ([x (simplify a context)]
                                           [y (simplify b context)])
                                       (cond [(equal? x #t) y]
                                             [(equal? y #t) #t]
                                             [(equal? x #f) #t]
                                             [(equal? y #f) (not x)]
                                             [else (list x 'implies y)]
                                             ))
                )
              )
        ))
 

; (evaluate expr context) -> boolean?
; expr: a valid representation of an expression
; context: list of pairs of the form (variable . #t/#f)
; Return the value of expr under the truth assignment context.
; Pre: every variable that appears in expr also appears in context.
(define (evaluate expr context)     ;; input is either boolean value, racket list, or variable
  (cond [(boolean? expr) expr]    ;; basecase, is a truth value
        [(variable? expr) (value-of expr context)] ;; basecase, is a variable
        [else                         ;; case, is an expression
         ;;if bracketing as a single list, reduce brackets
         (cond [(bracketed? expr) (evaluate (first expr) context)] ;; '((x)) -> '(x)
               [(binary? expr)
                ((value-of (binary-op expr) ops) (evaluate (left expr) context) (evaluate (right expr) context))]
               [(unary? expr)
                ((value-of (unary-op expr) ops) (evaluate (operand expr) context))]
               )
         ]
        )
  )

; (simplify expr context) -> valid expression
; expr: a valid representation of an expression
; context: list of pairs of the form (variable . #t/#f)
; Return an expression that is equivalent to expr,
; but is simplified as much as possible, according to
; the given rules.
(define (simplify expr context)     ;; input is either boolean value, racket list, or variable
  (cond [(boolean? expr) expr]    ;; basecase, is a truth value
        [(variable? expr) (value-of expr context)] ;; basecase, is a variable
        [else                           ;; case, is an expression
         ;;if bracketing as a single list, reduce brackets
         (cond [(bracketed? expr) (simplify (first expr) context)] ;; '((x)) -> '(x)
               [(binary? expr)
                ;; for every binary function, give it (a b context)          
                ((value-of (binary-op expr) simplifiers)
                 (left expr) (right expr) context)]
               [(unary? expr)
                ((value-of (unary-op expr) simplifiers) (operand expr) context)]
               )
         ]
        )
  )

