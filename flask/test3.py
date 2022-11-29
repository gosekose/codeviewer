import subprocess, sys
from subprocess import STDOUT, check_output

def get_build_version(command):

  out = subprocess.Popen(command, shell=True, stdout=subprocess.PIPE).stdout

  return out.read().strip()


if __name__ == '__main__':

  try:
    
    out = get_build_version('./run.sh')
    # out, err = out.communicate(timeout = 5)
    
    b = out.decode('utf-8')


    c = []
    
    # f = open(sys.argv[1], 'r') 
    f = open('output1.txt', 'r')
    lines = f.readlines()
    lines = list(map(lambda s: s.strip(), lines))
    f.close()

    print(b.split("\n") == lines)

    # b = out.decode('utf-8')

    c = []
    
    # f = open(sys.argv[1], 'r') 
    # f = open('output1.txt', 'r')
    # lines = f.readlines()
    # lines = list(map(lambda s: s.strip(), lines))
    # f.close()

    # print(b.split("\n") == lines)



  except:
    print("errors")

print('aaa')