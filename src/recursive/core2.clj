(ns recursive.core2
  (:require [quil.core :as q]
    [quil.middleware :as m]))

(def pi (Math/PI))

(defn setup []
  {:angle (/ pi 2) :time 200})

w
(defn sine-time
  "Devuelve una funcion seno mayor a 0 entre start y end"
  [time start end]
  (+ start (* (- end start) (Math/abs (Math/sin (/ (q/frame-count) time))))))

(def pi3 (/ (* 2 pi) 3))

(defn custom-circle [rad start end]
  (q/stroke 0)
  (q/fill 3)
  (q/arc 0 0 rad rad start end)
  (if (> rad 40)
    (let [rad (/ rad 1.2)]
      (custom-circle rad start (/ end 3))
      (custom-circle rad (+ start (/ (* end 2) 3)) end))))

(defn custom-circle [time rad start len]
    (q/no-stroke)
    (q/fill (sine-time time rad 255) 150 29)
    (q/arc 0 0 rad rad start (+ start len) :pie)
    (if (> rad 25)
      (let [len (/ len 3)
            rad (/ rad (sine-time time 1.4 4))]
        (q/with-rotation [(/ pi (sine-time time 2 16))]
          (custom-circle time rad start len)
          (custom-circle time rad (+ start (* 2 len)) len)))))

(defn custom-line [startx starty len angle]
    (q/stroke (* 255 angle) (* 800 angle) (* 800 angle))
    (if (> len 2)
      (let [len (/ len 1.8)]
        (q/with-rotation [angle]
          (q/line startx starty startx (- len))
          (q/with-translation [startx (- len)]
              (custom-line startx starty len  angle)))
        (q/with-rotation [(- angle)]
          (q/line startx starty startx (- len))
          (q/with-translation [startx (- len)]
              (custom-line startx starty len angle))))))

(defn mouse-to-values [start end input]
  (+ start (* (- end 1) (/ input (q/width)))))

(defn mouse-moved [state event]
  (assoc state :angle (mouse-to-values (/ pi 2) 0 (:x event))))

(defn draw [state]
  (q/with-translation [(/ (q/width) 2) (/ (q/height) 2)]
    ;(q/with-rotation [(:angle state)]
      (q/background 250)
      ;(custom-circle (:time state) 500 0 (* pi 2))))
      (q/line 0 100 0 0)
      (custom-line 0 0 100 (:angle state))))

(defn update-state [state]
  (assoc state :angle (sine-time 100 (/ pi 8) (/ pi 16))))

(q/defsketch recursive
  :size :fullscreen
  :display 2
   :setup setup
   :draw draw
   :update update-state
   ;:mouse-moved mouse-moved
   :features [:keep-on-top]
   :middleware [m/fun-mode])
