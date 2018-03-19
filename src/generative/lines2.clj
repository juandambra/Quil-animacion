(ns quil-intro.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil.helpers.seqs :refer [range-incl]]
            [quil.helpers.drawing :refer [line-join-points]]))

(defn rand-y
  [border-y]
  (+ border-y (q/noise (rand (- (q/height) (* 2 border-y))))))


(defn draw [state]
  ;(println state)
  (q/background 150)
  (q/stroke-weight 5)
  (q/smooth)
  (q/stroke 0 30)
  (q/line 20 50 480 50)

  (q/stroke 20 50 70)
  (let [step      (:step state)
        border-x  20
        border-y  10
        xs        (range-incl border-x (- (q/width) border-x) step)
        ys        (repeatedly #(rand-y border-y))
        line-args (line-join-points xs ys)]
    (dorun (map #(apply q/line %) line-args))))

(defn update-st [state]
  state)
;(update-in state [:step] inc))


(defn setup []
  (q/frame-rate 5)
  {:step 100})

(defn mouse-clicked [state event]
 (println state event)
 (if (= (:button event) :right) (println "RIGHT!") (println "LEFT!"))
 state)

(defn key-pressed [state event]
  (println state event)
  (let [step (:step state)]
    (assoc state :step (if (= (:key event) :left) (- step 10) (if (= (:key event) :right) (+ step 10) step)))))


(q/defsketch quil-intro
  :size [500 500]
  :setup setup
  :draw draw
  :update update-st
  ; :size :fullscreen
  :features [:keep-on-top]
  ;:mouse-clicked mouse-clicked
  :key-pressed key-pressed
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode])
