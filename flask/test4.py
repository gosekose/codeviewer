import subprocess, sys, os

def get_subprocess(command):

  out, err = subprocess.Popen(command, shell=True, stdin=subprocess.PIPE, stdout=subprocess.PIPE).communicate(timeout=2)

  return out, err



def python_problem_solve_test(filename, score) :
  
  result_score = 0
  result_text = {}

  try:

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
      
      command = ("python " + filename + " < " + str(length) + ".in")

      try:
        out, err = get_subprocess(command)

        try:
          answer = out.decode('utf-8').strip().split('\n')
          f = open('output' + str(length) + '.txt', 'r')
          result = f.readlines()
          result = list(map(lambda s: s.strip(), result))
          f.close()

          if (answer == result):
            result_score += score[length-1]
            result_text[str(length)] = "Success"
          else:
            result_text[str(length)] = "Wrong Answer"

        except:
          result_text["fail"] = "Compile Error"
          print("Compile Error")
          

      except:
        out.kill()
        err.kill()
        result_text["fail"] = "Runtime Error"
        print("Runtime error")
    
  except:
    result_text["fail"] = "Error"
    print("errors")


  return result_score, result_text

if __name__ == '__main__':

  score = [50, 50]
  result_score, result_text = python_problem_solve_test("test2.py", score)

  print(result_score)
  print(result_text)