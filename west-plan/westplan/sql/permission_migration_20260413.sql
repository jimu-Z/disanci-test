-- 权限迁移脚本（留言管理权限域收口）
-- 日期：2026-04-13
-- 目标：将历史权限 system:alumni_honor:reply 迁移为 system:message:reply
-- 说明：脚本幂等，可重复执行；包含备份、迁移、校验与回滚SQL。

-- =====================
-- 1) 迁移前备份
-- =====================
CREATE TABLE IF NOT EXISTS sys_menu_perm_mig_20260413_bak LIKE sys_menu;

INSERT INTO sys_menu_perm_mig_20260413_bak
SELECT s.*
FROM sys_menu s
WHERE s.perms = 'system:alumni_honor:reply'
  AND NOT EXISTS (
      SELECT 1
      FROM sys_menu_perm_mig_20260413_bak b
      WHERE b.menu_id = s.menu_id
  );

-- =====================
-- 2) 执行权限迁移
-- =====================
UPDATE sys_menu
SET perms = 'system:message:reply',
    update_by = 'migration_20260413',
    update_time = NOW()
WHERE perms = 'system:alumni_honor:reply';

-- =====================
-- 3) 迁移后校验
-- =====================
-- 3.1 查看是否仍有旧权限
SELECT menu_id, menu_name, perms
FROM sys_menu
WHERE perms = 'system:alumni_honor:reply';

-- 3.2 查看新权限菜单
SELECT menu_id, menu_name, perms
FROM sys_menu
WHERE perms = 'system:message:reply';

-- =====================
-- 4) 可选回滚（按需执行）
-- =====================
-- UPDATE sys_menu m
-- JOIN sys_menu_perm_mig_20260413_bak b ON b.menu_id = m.menu_id
-- SET m.perms = b.perms,
--     m.update_by = 'rollback_20260413',
--     m.update_time = NOW()
-- WHERE b.perms = 'system:alumni_honor:reply'
--   AND m.perms = 'system:message:reply';

-- 回滚后校验
-- SELECT menu_id, menu_name, perms
-- FROM sys_menu
-- WHERE perms IN ('system:alumni_honor:reply', 'system:message:reply');
