/**
 * A local utility to parse a unstructured paragraph of text into actionable tasks.
 * In a real-world scenario, this might call an LLM API, but here we'll use a rule-based
 * approach to split by punctuation/conjunctions and detect keywords for priority.
 */

export function parseBrainDump(text) {
  if (!text || text.trim() === '') return [];

  // 1. Split text into potential tasks using common delimiters (newlines, commas, "and", "also")
  // We use regex to split by common sentence boundaries or conjunctions
  let rawTasks = text.split(/(?:\n|\. |,| and | also | then )/i);

  // 2. Clean up and filter out empty strings
  rawTasks = rawTasks
    .map(t => t.trim())
    .filter(t => t.length > 2);

  // 3. Assign priority based on keywords
  const highPriorityKeywords = ['urgent', 'asap', 'tomorrow', 'important', 'need to', 'must'];
  const lowPriorityKeywords = ['maybe', 'eventually', 'someday', 'idea', 'might'];

  const tasks = rawTasks.map((taskText, index) => {
    let priority = 'medium'; // default
    const lowerText = taskText.toLowerCase();

    if (highPriorityKeywords.some(kw => lowerText.includes(kw))) {
      priority = 'high';
    } else if (lowPriorityKeywords.some(kw => lowerText.includes(kw))) {
      priority = 'low';
    }

    return {
      id: Date.now().toString() + index,
      title: taskText.charAt(0).toUpperCase() + taskText.slice(1), // Capitalize first letter
      priority: priority,
      completed: false,
    };
  });

  return tasks;
}
