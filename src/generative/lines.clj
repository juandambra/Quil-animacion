(ns quil-intro.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def pi 3.14159)


; (def ellip (vec (map rad1 (range 0 pi 0.1))))

(defn getx [[r a]]
  (* r (q/cos a)))

(defn gety [[r a]]
  (* r (q/sin a)))


(defn rad [a b angle]
  (/ (* a b) (q/sqrt (+ (q/sq (* a (q/cos angle))) (q/sq (* b (q/sin angle)))))))

(def rad1 (partial rad 150 200))

(def ellip (map (fn ([a] [(rad1 a) a])) (range 0 (* 2 pi) 0.1)))

(def ellip-xy (map (fn [a] [(+ (getx a) 200) (+ (gety a) 200)]) ellip))

(defn draw [state]
  ;(println state)
  (q/background 150)
  (q/stroke-weight 5)
  (let [colors (:c state)]
    (q/stroke (:r colors) (:g colors) (:b colors)))
  (q/line 30 30 (/ (q/height) 2) (/ (q/width) 2))
  (q/stroke (q/random 200))
  (q/stroke-weight 10)
  (let [linePos (:l state)]
    (q/line (q/mouse-x) (q/mouse-y) (:x linePos) (:y linePos))))

(defn update-st [state]
  (let [colors  (:c state)
        line    (:l state)
        xy      (nth ellip-xy (:n state))
        n       (:n state)]
    (assoc state :c (assoc colors :r (q/random 255) :g (q/random 255) :b (q/random 255))
                 :l (assoc line :x (first xy) :y (last xy))
                 :n (if (= (dec (count ellip-xy)) n) 0 (inc n)))))
(defn setup []
  (q/frame-rate 30)
  {:c {:r 25 :g 25 :b 25} :l {:x 500 :y 500} :n 0})

(defn mouse-clicked [state event]
 ;(println state event)
 state)

(q/defsketch quil-intro
  :size [500 500]
  :setup setup
  :draw draw
  :update update-st
  ; :size :fullscreen
  :features [:keep-on-top]
  ;:mouse-clicked mouse-clicked
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode])
