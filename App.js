import React, { useState } from 'react';
import { StatusBar } from 'expo-status-bar';
import { StyleSheet, View, SafeAreaView, TouchableOpacity, Text, KeyboardAvoidingView, Platform, ScrollView } from 'react-native';
import { theme } from './styles/theme';
import PomodoroTimer from './components/PomodoroTimer';
import BrainDumpInput from './components/BrainDumpInput';
import TaskList from './components/TaskList';

export default function App() {
  const [activeTab, setActiveTab] = useState('focus'); // 'focus' or 'tasks'
  const [tasks, setTasks] = useState([]);

  const handleTasksParsed = (newTasks) => {
    setTasks(prev => [...prev, ...newTasks].sort((a, b) => {
      // Sort by priority (high > medium > low)
      const priorityWeight = { high: 3, medium: 2, low: 1 };
      return priorityWeight[b.priority] - priorityWeight[a.priority];
    }));
  };

  const handleToggleTask = (taskId) => {
    setTasks(prev => prev.map(task => 
      task.id === taskId ? { ...task, completed: !task.completed } : task
    ));
  };

  const handleDeleteTask = (taskId) => {
    setTasks(prev => prev.filter(task => task.id !== taskId));
  };

  return (
    <SafeAreaView style={styles.safeArea}>
      <StatusBar style="light" />
      <KeyboardAvoidingView 
        style={styles.container}
        behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
      >
        <View style={styles.header}>
          <Text style={styles.headerTitle}>ProductivityHub</Text>
        </View>

        <View style={styles.tabContainer}>
          <TouchableOpacity 
            style={[styles.tab, activeTab === 'focus' && styles.activeTab]}
            onPress={() => setActiveTab('focus')}
          >
            <Text style={[styles.tabText, activeTab === 'focus' && styles.activeTabText]}>Focus</Text>
          </TouchableOpacity>
          <TouchableOpacity 
            style={[styles.tab, activeTab === 'tasks' && styles.activeTab]}
            onPress={() => setActiveTab('tasks')}
          >
            <Text style={[styles.tabText, activeTab === 'tasks' && styles.activeTabText]}>Tasks</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.content}>
          {activeTab === 'focus' ? (
            <PomodoroTimer />
          ) : (
            <View style={{flex: 1}}>
              <BrainDumpInput onTasksParsed={handleTasksParsed} />
              <TaskList 
                tasks={tasks} 
                onToggleTask={handleToggleTask} 
                onDeleteTask={handleDeleteTask} 
              />
            </View>
          )}
        </View>
      </KeyboardAvoidingView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: theme.colors.background,
  },
  container: {
    flex: 1,
  },
  header: {
    padding: theme.spacing.m,
    alignItems: 'center',
    borderBottomWidth: 1,
    borderBottomColor: theme.colors.border,
  },
  headerTitle: {
    fontSize: 24,
    fontWeight: 'bold',
    color: theme.colors.primary,
  },
  tabContainer: {
    flexDirection: 'row',
    padding: theme.spacing.m,
    gap: theme.spacing.s,
  },
  tab: {
    flex: 1,
    paddingVertical: theme.spacing.s,
    alignItems: 'center',
    borderRadius: theme.borderRadius.m,
    backgroundColor: theme.colors.surface,
  },
  activeTab: {
    backgroundColor: theme.colors.primary,
  },
  tabText: {
    color: theme.colors.textSecondary,
    fontWeight: 'bold',
  },
  activeTabText: {
    color: theme.colors.background,
  },
  content: {
    flex: 1,
    padding: theme.spacing.m,
  }
});
