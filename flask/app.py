from flask import Flask, request
from flask_restx import Resource, Api
import os, stat

app = Flask(__name__)
api = Api(app)

todos = {}
count = 1


@api.route('/api/v1/test/index/compile')
class CompileIndex(Resource):
    
    def get(self):

      
      filename = request.args.get("filename")

      path = "/home/koseyun/IdeaProjects/capston/main/source/index/"

      result = "suceess"

      full_file = path + filename + ".sh"

      first_list_count = os.listdir().count

      try:

        os.chmod(full_file, stat.S_IRWXU)

        os.path.join(path, filename)

        os.system(full_file)
        
        if os.listdir().count == first_list_count:
          result = "not_found_error"

      except:

        result = "comfile_error"


      return {
            'filename': filename,
            'result': result
        }


@api.route('/todos')
class TodoPost(Resource):
    def post(self):
        global count
        global todos
        
        idx = count
        count += 1
        todos[idx] = request.json.get('data')
        
        return {
            'todo_id': idx,
            'data': todos[idx]
        }


@api.route('/todos/<int:todo_id>')
class TodoSimple(Resource):
    def get(self, todo_id):
        return {
            'todo_id': todo_id,
            'data': todos[todo_id]
        }

    def put(self, todo_id):
        todos[todo_id] = request.json.get('data')
        return {
            'todo_id': todo_id,
            'data': todos[todo_id]
        }
    
    def delete(self, todo_id):
        del todos[todo_id]
        return {
            "delete" : "success"
        }

if __name__ == "__main__":
    app.run(debug=True, host='0.0.0.0', port=5000)