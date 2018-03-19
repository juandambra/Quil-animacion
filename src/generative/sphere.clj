(ns quil-intro.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil.helpers.seqs :refer [range-incl]]
            [quil.helpers.drawing :refer [line-join-points]]))


(defn getx [[r a o]] (* r (q/sin a) (q/cos o)))
(defn gety [[r a o]] (* r (q/sin a) (q/sin o)))
(defn getz [[r a o]] (* r (q/cos a)))

(def pi 3.14159)

(defn draw [state]
  ;(println state)
  (q/background 255)
  (q/stroke-weight 14)
  (q/point 0 0 0)
  (q/stroke-weight 3)
  (q/begin-shape :quad-strip)
  (doseq [a (range 0 (* 2 pi) 0.2)
          o (range 0 (* 2 pi) 0.1)
          :let [x (getx [(+ 300 (- (rand 30) 0.5)) a o])
                y (gety [(+ 300 (- (rand 20) 0.5)) a o])
                z (getz [(+ 300 (- (rand 20) 0.5)) a o])]]
         ;(println a o))
         ;(q/points x y z)
         (q/vertex x y z))
  (q/end-shape))


(defn update-st [state]
  state)

(defn setup []
  (q/frame-rate 30))

(rand)

(q/defsketch quil-intro
  :size [800 800]
  :setup setup
  :draw draw
  :update update-st
  :renderer :p3d
  ; :size :fullscreen
  :features [:keep-on-top]
  ; :mouse-clicked mouse-clicked
  ; :key-pressed key-pressed
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode m/navigation-3d])
