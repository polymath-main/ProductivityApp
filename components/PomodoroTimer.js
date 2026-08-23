import React, { useState, useEffect, useRef } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, Animated, Easing } from 'react-native';
import { theme } from '../styles/theme';

export default function PomodoroTimer() {
  const FOCUS_TIME = 25 * 60; // 25 minutes
  const BREAK_TIME = 5 * 60; // 5 minutes

  const [timeLeft, setTimeLeft] = useState(FOCUS_TIME);
  const [isActive, setIsActive] = useState(false);
  const [isFocusMode, setIsFocusMode] = useState(true);

  const breathAnim = useRef(new Animated.Value(1)).current;
  const glowAnim = useRef(new Animated.Value(0)).current;

  useEffect(() => {
    let interval = null;
    if (isActive && timeLeft > 0) {
      interval = setInterval(() => {
        setTimeLeft((time) => time - 1);
      }, 1000);
      
      Animated.loop(
        Animated.sequence([
          Animated.timing(breathAnim, {
            toValue: 1.05,
            duration: 2000,
            easing: Easing.inOut(Easing.ease),
            useNativeDriver: true,
          }),
          Animated.timing(breathAnim, {
            toValue: 1,
            duration: 2000,
            easing: Easing.inOut(Easing.ease),
            useNativeDriver: true,
          })
        ])
      ).start();

      Animated.loop(
        Animated.sequence([
          Animated.timing(glowAnim, {
            toValue: 1,
            duration: 2000,
            easing: Easing.inOut(Easing.ease),
            useNativeDriver: false, // Box shadow doesn't support native driver well
          }),
          Animated.timing(glowAnim, {
            toValue: 0.5,
            duration: 2000,
            easing: Easing.inOut(Easing.ease),
            useNativeDriver: false,
          })
        ])
      ).start();

    } else {
      Animated.timing(breathAnim, {
        toValue: 1,
        duration: 500,
        useNativeDriver: true,
      }).start();
      Animated.timing(glowAnim, {
        toValue: 0,
        duration: 500,
        useNativeDriver: false,
      }).start();

      if (timeLeft === 0) {
        setIsFocusMode(!isFocusMode);
        setTimeLeft(!isFocusMode ? FOCUS_TIME : BREAK_TIME);
        setIsActive(false);
      }
    }
    return () => {
      clearInterval(interval);
      breathAnim.stopAnimation();
      glowAnim.stopAnimation();
    };
  }, [isActive, timeLeft, isFocusMode]);

  const toggleTimer = () => setIsActive(!isActive);
  
  const resetTimer = () => {
    setIsActive(false);
    setTimeLeft(isFocusMode ? FOCUS_TIME : BREAK_TIME);
  };

  const formatTime = (seconds) => {
    const mins = Math.floor(seconds / 60);
    const secs = seconds % 60;
    return `${mins.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;
  };

  const glowColor = glowAnim.interpolate({
    inputRange: [0, 1],
    outputRange: ['rgba(0,0,0,0)', isFocusMode ? theme.colors.primaryGlow : 'rgba(16, 185, 129, 0.4)']
  });

  return (
    <View style={styles.container}>
      <Text style={styles.modeText}>
        {isFocusMode ? 'Focus Session' : 'Break Time'}
      </Text>
      <Animated.View style={[
        styles.timerCircle, 
        !isFocusMode && styles.breakCircle,
        { transform: [{ scale: breathAnim }] },
        isActive && { shadowColor: isFocusMode ? theme.colors.primary : theme.colors.secondary, shadowOffset: { width: 0, height: 0 }, shadowOpacity: glowAnim, shadowRadius: 20, elevation: 10 }
      ]}>
        <Animated.View style={[StyleSheet.absoluteFill, styles.glowBackground, { backgroundColor: glowColor }]} />
        <Text style={styles.timeText}>{formatTime(timeLeft)}</Text>
      </Animated.View>
      <View style={styles.buttonContainer}>
        <TouchableOpacity style={styles.button} onPress={toggleTimer} activeOpacity={0.8}>
          <Text style={styles.buttonText}>{isActive ? 'Pause' : 'Start'}</Text>
        </TouchableOpacity>
        <TouchableOpacity style={[styles.button, styles.resetButton]} onPress={resetTimer} activeOpacity={0.8}>
          <Text style={styles.buttonText}>Reset</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    alignItems: 'center',
    padding: theme.spacing.xl,
  },
  modeText: {
    color: theme.colors.textSecondary,
    fontSize: 22,
    marginBottom: theme.spacing.l,
    fontWeight: '600',
    letterSpacing: 1,
  },
  timerCircle: {
    width: 260,
    height: 260,
    borderRadius: 130,
    borderWidth: 4,
    borderColor: theme.colors.primary,
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: theme.spacing.xl,
    backgroundColor: theme.colors.surface,
    overflow: 'hidden',
  },
  glowBackground: {
    borderRadius: 130,
  },
  breakCircle: {
    borderColor: theme.colors.secondary,
  },
  timeText: {
    color: theme.colors.text,
    fontSize: 72,
    fontWeight: '300',
    fontVariant: ['tabular-nums'],
  },
  buttonContainer: {
    flexDirection: 'row',
    gap: theme.spacing.m,
  },
  button: {
    backgroundColor: theme.colors.primary,
    paddingVertical: theme.spacing.m,
    paddingHorizontal: theme.spacing.xl,
    borderRadius: theme.borderRadius.xl,
    minWidth: 130,
    alignItems: 'center',
    shadowColor: theme.colors.primary,
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.3,
    shadowRadius: 8,
    elevation: 5,
  },
  resetButton: {
    backgroundColor: theme.colors.surface,
    borderWidth: 1,
    borderColor: theme.colors.border,
    shadowOpacity: 0,
    elevation: 0,
  },
  buttonText: {
    color: theme.colors.text,
    fontSize: 16,
    fontWeight: '600',
    letterSpacing: 0.5,
  },
});
