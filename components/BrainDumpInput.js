import React, { useState } from 'react';
import { View, TextInput, TouchableOpacity, Text, StyleSheet } from 'react-native';
import { theme } from '../styles/theme';
import { parseBrainDump } from '../services/TaskParser';

export default function BrainDumpInput({ onTasksParsed }) {
  const [text, setText] = useState('');

  const handleProcess = () => {
    const tasks = parseBrainDump(text);
    if (tasks.length > 0) {
      onTasksParsed(tasks);
      setText(''); // Clear input after processing
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Brain Dump</Text>
      <Text style={styles.subtitle}>Type everything on your mind. We'll sort it out.</Text>
      
      <TextInput
        style={styles.input}
        multiline
        placeholder="e.g. Need to call mom, also finish the report asap, and maybe read a book..."
        placeholderTextColor={theme.colors.textSecondary}
        value={text}
        onChangeText={setText}
        textAlignVertical="top"
      />
      
      <TouchableOpacity 
        style={[styles.button, text.trim() === '' && styles.buttonDisabled]} 
        onPress={handleProcess}
        disabled={text.trim() === ''}
      >
        <Text style={styles.buttonText}>Process Thoughts ✨</Text>
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
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    color: theme.colors.text,
    marginBottom: theme.spacing.s,
  },
  subtitle: {
    fontSize: 14,
    color: theme.colors.textSecondary,
    marginBottom: theme.spacing.m,
  },
  input: {
    backgroundColor: theme.colors.background,
    color: theme.colors.text,
    borderRadius: theme.borderRadius.m,
    padding: theme.spacing.m,
    minHeight: 120,
    fontSize: 16,
    borderWidth: 1,
    borderColor: theme.colors.border,
    marginBottom: theme.spacing.m,
  },
  button: {
    backgroundColor: theme.colors.primary,
    padding: theme.spacing.m,
    borderRadius: theme.borderRadius.m,
    alignItems: 'center',
  },
  buttonDisabled: {
    backgroundColor: theme.colors.border,
  },
  buttonText: {
    color: theme.colors.text,
    fontSize: 16,
    fontWeight: 'bold',
  }
});
