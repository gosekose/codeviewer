from flask import Flask, request, Response, make_response, jsonify
from flask_restx import Resource, Api
import os, stat, subprocess

from flask_cors import CORS

app = Flask(__name__)
api = Api(app)

cors = CORS(app, resources={r"/api/*": {"origins": "*"}})


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
            os.system(full_file)
        
            if os.listdir().count == first_list_count:
                result = "not_found_error"

        except:

            result = "comfile_error"

        response = make_response(jsonify({
            'filename': filename,
            'result': result
        }))

        response.headers.add("Access-Control-Allow-Origin", "*")
        response.headers.add('Access-Control-Allow-Headers', "*")
        response.headers.add('Access-Control-Allow-Methods', "*")

        return response
        
        
@api.route('/api/server/solve/test')
class RequestSolveServer(Resource):

    def get_subprocess(self, command):

        out, err = subprocess.Popen(command, shell=True, stdin=subprocess.PIPE, stdout=subprocess.PIPE).communicate(timeout=2)

        return out, err


    def python_problem_solve_test(self, problem_path, solve_request_url, score) :
    
        result_score = 0
        result_text = {}

        try:
            
            os.chdir(problem_path)
            oslist = os.listdir()
            oslist.sort()

            inputs, outputs = [], []
            
            for o in oslist:

                if o.split('.')[-1] == 'in':
                    inputs.append(o)
                elif o.split('.')[-1] == 'out':
                    outputs.append(o)

                if (len(inputs) == len(outputs)):
                    full_lenth = len(inputs)
                else:
                    full_lenth = min(len(inputs), len(outputs))
            

            for length in range(1, full_lenth+1):
            
                command = ("python " + solve_request_url + " < " + str(length) + ".in")

                try:
                    out, err = self.get_subprocess(command)

                    try:
                        answer = out.decode('utf-8').strip().split('\n')
                        f = open('output' + str(length) + '.txt', 'r')
                        result = f.readlines()
                        result = list(map(lambda s: s.strip(), result))
                        f.close()

                        if (answer == result):
                            result_score += int(score[length-1])
                            result_text[str(length)] = "Success"
                            
                        else:
                            result_text[str(length)] = "Wrong Answer"

                    except:
                        result_text["fail"] = "Compile Error"
                    
                except:
                    out.kill()
                    err.kill()
                    result_text["fail"] = "Runtime Error"
            
        except:
            result_text["fail"] = "Error"


        return result_score, result_text    



    def get(self):

        try:

            problem_url = request.args.get("problemUrl")
            solve_id = request.args.get("solveId")
            score = request.args.getlist("score")
            solve_request_url = request.args.get("solveRequestUrl")
            
            result_score, result_text = self.python_problem_solve_test("test2.py", problem_url, solve_request_url, score)
            
            print("result_score = ", result_score)
            print("result_text = ", result_text)

            response = make_response(jsonify({
                "solveId": solve_id,
                "resultScore": result_score,
                "resultStatus": result_text
            }))
        
        except :
            response = make_response(jsonify({
                "solveId": solve_id,
                "resultScore": 0,
                "resultStatus": "Server Error"
            }))

        response.headers.add("Access-Control-Allow-Origin", "*")
        response.headers.add('Access-Control-Allow-Headers', "*")
        response.headers.add('Access-Control-Allow-Methods', "*")

        print(response)

        return response

  


@api.route('/api/server/problem/demo/test')
class RequestProblemDemoServer(Resource):

    def get_subprocess(self, command):

        out, err = subprocess.Popen(command, shell=True, stdin=subprocess.PIPE, stdout=subprocess.PIPE).communicate(timeout=2)

        return out, err


    def python_problem_create_test(self, main_file_Path, folder_path) :
    
        self.io_file_result = True
        self.io_process_result = True
        self.runtime_result = True
        self.server_result = True

        try:
            
            os.chdir(folder_path)
            oslist = os.listdir()
            oslist.sort()

            inputs, outputs = [], []
            
            for o in oslist:

                if o.split('.')[-1] == 'in':
                    inputs.append(o)
                elif o.split('.')[-1] == 'out':
                    outputs.append(o)

                if (len(inputs) == len(outputs)):
                    full_lenth = len(inputs)
                else:
                    full_lenth = min(len(inputs), len(outputs))
            

            for length in range(1, full_lenth+1):
            
                command = ("python " + main_file_Path + " < " + str(length) + ".in")
                print(command)

                try:
                    out, err = self.get_subprocess(command)

                    try:
                        answer = out.decode('utf-8').strip().split('\n')
                        f = open(str(length) + '.out', 'r')
                        result = f.readlines()
                        result = list(map(lambda s: s.strip(), result))
                        f.close()

                        if (answer != result):
                            self.io_file_result = False

                    except:
                        self.io_process_result = False
                    
                except:
                    out.kill()
                    err.kill()
                    self.runtime_result = False
            
        except:
            self.server_result = False

        return self.io_file_result, self.io_process_result, self.runtime_result, self.server_result



    def get(self):

        try:

            main_file_path = request.args.get("mainFilePath")
            folder_path = request.args.get("folderPath")

            io_file_result, io_process_result, runtime_result, server_result = self.python_problem_create_test(main_file_path, folder_path)
            
            response = make_response(jsonify({
                "ioFileResult": io_file_result,
                "ioProcessResult": io_process_result,
                "runtimeResult": runtime_result,
                "serverResult": server_result
            }))
        
        except :

            response = make_response(jsonify({
                "ioFileResult": False,
                "ioProcessResult": False,
                "runtimeResult": False,
                "serverResult": False
            }))

        response.headers.add("Access-Control-Allow-Origin", "*")
        response.headers.add('Access-Control-Allow-Headers', "*")
        response.headers.add('Access-Control-Allow-Methods', "*")

        print(response)

        return response


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
