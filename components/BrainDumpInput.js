import React, { useState, useRef } from 'react';
import { View, TextInput, TouchableOpacity, Text, StyleSheet, Animated } from 'react-native';
import { theme } from '../styles/theme';
import { parseBrainDump } from '../services/TaskParser';

export default function BrainDumpInput({ onTasksParsed }) {
  const [text, setText] = useState('');
  const [isFocused, setIsFocused] = useState(false);
  const focusAnim = useRef(new Animated.Value(0)).current;

  const handleProcess = () => {
    // Check if parseBrainDump exists, else fallback to a simple mock or default behavior
    const tasks = typeof parseBrainDump === 'function' 
      ? parseBrainDump(text) 
      : text.split(',').map(t => ({ id: Math.random().toString(), title: t.trim(), priority: 'medium', completed: false })).filter(t => t.title);
      
    if (tasks.length > 0) {
      onTasksParsed(tasks);
      setText(''); // Clear input after processing
    }
  };

  const handleFocus = () => {
    setIsFocused(true);
    Animated.timing(focusAnim, {
      toValue: 1,
      duration: 300,
      useNativeDriver: false,
    }).start();
  };

  const handleBlur = () => {
    setIsFocused(false);
    Animated.timing(focusAnim, {
      toValue: 0,
      duration: 300,
      useNativeDriver: false,
    }).start();
  };

  const borderColor = focusAnim.interpolate({
    inputRange: [0, 1],
    outputRange: [theme.colors.border, theme.colors.primary]
  });

  const shadowOpacity = focusAnim.interpolate({
    inputRange: [0, 1],
    outputRange: [0, 0.4]
  });

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Brain Dump</Text>
      <Text style={styles.subtitle}>Type everything on your mind. We'll sort it out.</Text>
      
      <Animated.View style={[
        styles.inputContainer,
        { 
          borderColor,
          shadowColor: theme.colors.primary,
          shadowOffset: { width: 0, height: 0 },
          shadowOpacity,
          shadowRadius: 10,
          elevation: isFocused ? 5 : 0,
        }
      ]}>
        <TextInput
          style={styles.input}
          multiline
          placeholder="e.g. Need to call mom, finish the report..."
          placeholderTextColor={theme.colors.textSecondary}
          value={text}
          onChangeText={setText}
          onFocus={handleFocus}
          onBlur={handleBlur}
          textAlignVertical="top"
        />
      </Animated.View>
      
      <TouchableOpacity 
        style={[styles.button, text.trim() === '' && styles.buttonDisabled]} 
        onPress={handleProcess}
        disabled={text.trim() === ''}
        activeOpacity={0.8}
      >
        <Text style={[styles.buttonText, text.trim() === '' && styles.buttonTextDisabled]}>Process Thoughts ✨</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: theme.colors.surface,
    padding: theme.spacing.l,
    borderRadius: theme.borderRadius.l,
    marginBottom: theme.spacing.m,
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.05)',
  },
  title: {
    fontSize: 26,
    fontWeight: '800',
    color: theme.colors.text,
    marginBottom: theme.spacing.s,
    letterSpacing: -0.5,
  },
  subtitle: {
    fontSize: 15,
    color: theme.colors.textSecondary,
    marginBottom: theme.spacing.l,
    fontWeight: '400',
  },
  inputContainer: {
    backgroundColor: theme.colors.background,
    borderRadius: theme.borderRadius.m,
    borderWidth: 1.5,
    marginBottom: theme.spacing.l,
    overflow: 'hidden',
  },
  input: {
    color: theme.colors.text,
    padding: theme.spacing.m,
    minHeight: 120,
    fontSize: 16,
    lineHeight: 24,
  },
  button: {
    backgroundColor: theme.colors.primary,
    padding: theme.spacing.m,
    borderRadius: theme.borderRadius.m,
    alignItems: 'center',
    shadowColor: theme.colors.primary,
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.3,
    shadowRadius: 8,
    elevation: 4,
  },
  buttonDisabled: {
    backgroundColor: theme.colors.border,
    shadowOpacity: 0,
    elevation: 0,
  },
  buttonText: {
    color: theme.colors.background, // Make text dark on the vibrant primary button for better contrast
    fontSize: 16,
    fontWeight: 'bold',
    letterSpacing: 0.5,
  },
  buttonTextDisabled: {
    color: theme.colors.textSecondary,
  }
});
