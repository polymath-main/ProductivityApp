import sys
import json
sys.path.append("/data/data/com.termux/files/home/.gemini/config/plugins/nodeshub/engine")
try:
    from agent_engine import get_spatial_context
    print("NodesHub Engine initialized. Fetching spatial context for the blueprints...")
    try:
        nodes = get_spatial_context("productivity_architecture_blueprint", radius=5.0)
        print("Productivity Nodes:", nodes)
    except Exception as e:
        print("Error getting nodes:", e)
except Exception as e:
    print("Could not import engine:", e)
