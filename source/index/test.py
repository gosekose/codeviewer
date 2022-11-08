import os, stat



try:

  os.chmod("./T_A1499510A35A0836B4F14B8FA37BDF56_Test.sh", stat.S_IRWXU)
  os.system("./T_A1499510A35A0836B4F14B8FA37BDF56_Test.sh")

except:

  print("error")


print("file test close")