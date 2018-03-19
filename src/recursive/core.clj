(ns recursive.core
  (:require [quil.core :as q]
    [quil.middleware :as m]))

(defn setup []
  {:angle 0})

(defn custom-circle [x y rad]
  (q/stroke 10)
  (q/no-fill)
  (q/ellipse x y rad rad)
  (if (> rad 15)
    (do
      (let [rad (/ rad (+ 2 (* 15 (Math/abs (Math/sin (* 0.001 (q/frame-count)))))))]
        (custom-circle (+ x rad) y rad)
        (custom-circle (- x rad) y rad)
        (custom-circle x (+ y rad) rad)
        (custom-circle x (- y rad) rad)))))

(defn draw [state]
  (q/with-translation [(/ (q/width) 2) (/ (q/height) 2)]
    (q/with-rotation [(:angle state)]
      (q/background 250)
      (custom-circle 0 0 300))))

(defn update-state [state]
  (assoc state :angle (+ (:angle state) 0.01)))

(q/defsketch recursive
  :size :fullscreen
  :display 2
  :setup setup
  :draw draw
  :update update-state
  ;:mouse-clicked mouse-click
  :features [:keep-on-top]
  :middleware [m/fun-mode])
