(ns quil-intro.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def points-init [[[-20 -2 1] 1] [[100 -2 1] 1] [[4 43 1] -1] [[4 54 1] -1]])

(defn act [x] (if (> x 0) 1 -1))

(defn dot [x y] (reduce + (map * x y)))

(def mult (fn [vecc z] (reduce (fn [x y] (conj x (* y z))) [] vecc)))

(defn perceptron
  [w vec]
  (if (not= (act (dot w (get vec 0))) (get vec 1))
    (map + w (mult (get vec 0) (get vec 1)))
    w))

(defn new-point [x y c]
 ; (q/fill 100 (* (Math/sin (/ (q/frame-count) 10)) 255) (* (radial (mouse-centered)) (* rad 0.01)))
 (let [x (- x (/ (q/width) 2))]
  (let [class {:left 1 :right 0}
        x (- x (/ (q/width) 2))
        y (- y (/ (q/height) 2))]
   [[x y 1] (class c 1)])))

(def line (fn [x w] (/ (+ (* x (get (vec w) 0)) (get (vec w) 2)) (- (get (vec w) 1)))))

(defn drawLine [w]
  (q/stroke-weight 0.2)
  (q/stroke 255 100 0)
  (q/line [(q/width) (line (q/width) w)] [(- (q/width)) (line (- (q/width)) w)]))

(defn cycle-vec [points]
 (conj (vec (rest points)) (first points)))

(defn mouse-clicked [state event]
 (let [[x y c] event]
  (assoc state :points (conj (:points state) (new-point x y c)))))


(defn update-st [state]
 (let [points (:points state)]
  (assoc state :w (perceptron (:w state) (first (:points state)))
         :points (cycle-vec points))))


(defn drawPoints [points scale]
  (q/scale scale)
  (q/stroke-weight 5)
  (doseq [point points]
    (let [x (get (get point 0) 0)
          y (get (get point 0) 1)
          color (get point 1)]
         (q/stroke 20 100 (* color 255))
         (q/point x y))))

(defn draw [state]
  (q/background 255)
  (q/with-translation [(/ (q/width) 2) (/ (q/height) 2)]
    (drawPoints (:points state) (:scale state))
    (drawLine (:w state))))

(defn setup []
  ;(q/frame-rate 60)
  {:w [1 1 1] :points points-init :scale 1})

(q/defsketch quil-intro
  :size :fullscreen
  :display 2
  :setup setup
  :draw draw
  :update update-st
  :features [:present]
  :mouse-clicked mouse-clicked
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode])
