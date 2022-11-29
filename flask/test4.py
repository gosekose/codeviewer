import subprocess, sys, os

def get_build_version(command):

  out, err = subprocess.Popen(command, shell=True, stdin=subprocess.PIPE, stdout=subprocess.PIPE).communicate(timeout=10)

  return out, err


if __name__ == '__main__':

  try:

    # a = os.listdir()
    # a.sort()

    # inputs = []
    # outputs = []
    
    # for i in a:
    #   print(i)
    #   if i[0:5] == "input":
    #     inputs.append(i)
    #   elif i[0:5] == "ouput":
    #     outputs.append(i)

    #   if (len(inputs) == len(outputs)):
    #     full_lenth = len(inputs)
    #   else:
    #     full_lenth = min(len(inputs), len(outputs))

    # for i in full_lenth:
    #   out, err = get_build_version('./run.sh')
    #   b = out.decode('utf-8').strip().split('\n')

    out, err = get_build_version('./run.sh')
    b = out.decode('utf-8').strip().split('\n')
    print(b)

    # c = []
    
    # # f = open(sys.argv[1], 'r') 

    f = open('output1.txt', 'r')
    lines = f.readlines()
    lines = list(map(lambda s: s.strip(), lines))
    f.close()
    print(lines)

    print(b == lines)
    
  except:
    print("errors")

print('aaa')