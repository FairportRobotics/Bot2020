import sys
import cv2
import numpy as np
from networktables import NetworkTables as nt
import logging as log

log.basicConfig(level = log.DEBUG)

cap = cv2.VideoCapture(0) # ID/port of the camera

while(True):
    ret, frame = cap.read()
    blur = cv2.blur(frame, (11, 11))
    b, g, r = cv2.split(frame)
    thresh = cv2.threshold(g, 200, 255, cv2.THRESH_BINARY)[1]
    mask = cv2.bitwise_and(blur, blur, thresh)
    norm = cv2.normalize(mask, None, 0, 255, cv2.NORM_MINMAX)
    temp = cv2.cvtColor(norm, cv2.COLOR_BGR2HSV)
    hsv = cv2.inRange(temp, (0, 0, 232),  (180, 255, 255))
    erode = cv2.erode(hsv, None, cv2.BORDER_CONSTANT)
    dilate = cv2.dilate(erode, None, cv2.BORDER_CONSTANT)
    contours, hierarchy = cv2.findContours(dilate, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

#    contour = contours[1] # Specified contour
    contour = max(contours, key = cv2.contourArea) # Contour with max area
#    out = cv2.drawContours(img, contours, 3, (255,0,0), 1) # Draw 4th contour
#    out = cv2.drawContours(img, contours, -1, (255,0,0), 1) # Draw all contours

    epsilon = 0.1 * cv2.arcLength(contour, True) # Adjust the constant to change accuracy (lower = higher accuracy)
    approx = cv2.approxPolyDP(contour, epsilon, True)
    out = cv2.drawContours(frame, [approx], 0, (255,0,0), 1)

    # Centroid
    moments = cv2.moments(approx)
    cx = int(moments['m10'] / moments['m00'])
    cy = int(moments['m01'] / moments['m00'])

    # Best fit line
    rows, cols = out.shape[:2]
    [vx, vy, x, y] = cv2.fitLine(contour, cv2.DIST_L2, 0, 0.01, 0.01)
    lefty = int((-x * vy / vx) + y) # Left y average (on perimeter)
    righty = int(((cols - x) * vy / vx) + y) # Right y average (on perimeter)
    outWithLine = cv2.line(out, (cols - 1 , righty), (0, lefty), (255, 0, 0), 1)

    # Area & Perimeter
    area = cv2.contourArea(approx)
    per = cv2.arcLength(approx, True)
    # points = np.transpose(np.nonzero(out))

    # NetworkTables time! Yay! ...
    if len(sys.argv) != 2:
        print("Error: specify an IP to connect to!")
        exit(0)

        ip = sys.argv[1]
        nt.initialize(server = ip)
        sd = nt.getTable("SmartDashboard")

        sd.putNumber("center-x", cx)
        sd.putNumber("center-y", cy)
        sd.putNumber("lefty", lefty)
        sd.putNumber("righty", righty)
        sd.putNumber("area", area)
        sd.putNumber("per", per)

    cv2.imshow('Processed Camera Feed', outWithLine)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()
