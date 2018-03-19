(ns quil-intro.elipses
  (:require [quil.core :as q]
            [quil.middleware  :as m]))

(defn setup []
  {:width (q/width) :rad 500})

(defn radial [vec]
  (let [x (:x vec)
        y (:y vec)]
    (Math/sqrt (+ (* x x) (* y y)))))

(defn mouse-centered []
  {:x (- (q/mouse-x) (/ (q/width) 2))
   :y (- (q/mouse-y) (/ (q/height) 2))})

(defn custom-circle [x y rad]
  (q/stroke 0)
  (q/fill (* rad 0.01 (radial (mouse-centered))))
  (q/ellipse x y rad rad)
  (if (> rad 2)
    (let [rad (* rad 0.85 (Math/abs (Math/sin (* (q/frame-count) 0.04))))]
      (recur 0 0 rad))))

(defn draw  [state]
  (q/background 10)
  (q/with-translation [(/ (q/width) 2) (/ (q/height) 2)]
    (custom-circle 0 0 (:rad state))))

(defn new-rad [rad]
  ;(Math/abs (* 500 (Math/sin (/ (q/frame-count) 10000)))))
  rad)

(defn update-state [state]
  ;(println "R")
  (assoc state :rad (new-rad (:rad state))))

(defn mouse-click [state event]
 (println "Clicked")
 state)

(q/defsketch quil-intro
  :size :fullscreen
  :display 2
  :setup setup
  :draw draw
  :update update-state
  :mouse-clicked mouse-click
  :features [:keep-on-top]
  :middleware [m/fun-mode])
