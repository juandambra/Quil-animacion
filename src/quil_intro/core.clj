(ns quil-intro.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn f [t]
  [(* t (q/sin t))
   (* t (q/cos t))])

(defn draw-plot [f from to step]
  (doseq [two-points (->> (range from to step)
                          (map f)
                          (partition 2 1))]
    (apply q/line two-points)))

(defn draw-anim []
  (let [t (/ (q/frame-count) 2000)]
    (q/stroke 1)
    (q/line (f t) (f (+ t 1)))))

(defn draw []
  (q/background 255)
  (q/with-translation [(/ (q/width) 2) (/ (q/height) 2)]
    (draw-plot f 0 100 (/ (q/frame-count) (+ (* (/ (q/mouse-y) (q/height)) 1000) 0.001)))))

(defn setup []
  (q/frame-rate 60))


(q/defsketch quil-intro
  ;:title "You spin my circle right round"
  :size :fullscreen
  :display 2
  :setup setup
  :draw draw
  :features [:keep-on-top])
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  ;:middleware [m/fun-mode])
